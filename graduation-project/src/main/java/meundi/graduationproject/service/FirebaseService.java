package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.DTO.ChatRoomDTO;
import meundi.graduationproject.domain.DTO.MessagesDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.repository.FirebaseRepositoryDao;
import meundi.graduationproject.repository.MemberRepositoryUsingJPA;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseService {

    private final FirebaseRepositoryDao firebaseRepositoryDao;
    private final MailService mailService;
    private final MemberRepositoryUsingJPA memberRepository;

    public List<MessagesDTO> getMessages(String roomId) throws ExecutionException, InterruptedException {
        return firebaseRepositoryDao.getMessages(roomId);
    }

    public List<ChatRoomDTO> getUserChatRooms(Long userId) throws Exception {
        return  firebaseRepositoryDao.getUserChatRooms(userId);
    }

    public void sendMessage(MessagesDTO message) throws Exception {
        firebaseRepositoryDao.sendMessage(message);
    }

    public void createChatRoom(ChatRoomDTO chatRoom) {
        firebaseRepositoryDao.createChatRoom(chatRoom);
    }

    public ChatRoomDTO findChatRoom(String roomId) throws Exception {
        return firebaseRepositoryDao.getChatRoom(roomId);
    }

    public List<ChatRoomDTO> getChatRoomAll() throws Exception {
        return firebaseRepositoryDao.getChatRooms();
    }

    public Map<String, Object> joinRoom(ChatRoomDTO room, Member member) throws Exception {
        Map<String, Object> result = new HashMap<>();

        // 이미 참여한 사용자인지 확인
        if (room.getParticipants().containsKey(member.getId().toString())) {
            log.info("already participated");
            result.put("accessible", true);
            // enter 시 true -> false 로
            firebaseRepositoryDao.enterRoom(room, member.getId().toString());
            return result;
        }

        // 참여자가 아니라면 정원이 찼는지 확인
        if (room.getParticipants().size() == room.getMax()) {
            log.info("room already full");
            result.put("accessible", false);
            result.put("message", "인원이 가득 찼습니다");
            return result;
        }

        // 나잇대가 맞는지 확인
        if (!room.getAvailableAgeRange().equals("any") && !room.getAvailableAgeRange().equals(member.getAge_range())) {
            log.info("not mismatch age");
            result.put("accessible", false);
            result.put("message", "해당하는 범위의 나이가 아닙니다");
            return result;
        }

        if (!room.getGender().equals("any") && !room.getGender().equals(member.getGender())) {
            log.info("not mismatch gender");
            result.put("accessible", false);
            result.put("message", "해당하는 성별이 아닙니다");
            return result;
        }

        result.put("accessible", true);
        firebaseRepositoryDao.join(room, member.getId());
        return result;
    }

    // 2시간마다 채팅방에 업데이트 된 내용이 있으면 실행됨
    @Scheduled(cron = "0 0 0/2 * * *", zone = "Asia/Seoul")
    public void sendNotify() throws Exception {
        List<ChatRoomDTO> rooms = firebaseRepositoryDao.getChatRooms();
        for (ChatRoomDTO room : rooms) {
            ArrayList<String> receivers = new ArrayList<>();

            if (room.getIsUpdated()) {
                for (String id : room.getParticipants().keySet()) {
                    if (room.getParticipants().get(id)) {
                        if (memberRepository.findByIdEquals(Long.parseLong(id)) == null) {
                            continue;
                        }
                        String email = memberRepository.findByIdEquals(Long.parseLong(id)).getEmail();
                        receivers.add(email);
                        room.getParticipants().put(id, false);
                    }
                }

                if (!receivers.isEmpty()) {
                    mailService.sendNotify(room.getRoomId(), receivers);
                }
                room.setIsUpdated(false);
                firebaseRepositoryDao.updateRoomInfo(room);
            }
        }
    }
}