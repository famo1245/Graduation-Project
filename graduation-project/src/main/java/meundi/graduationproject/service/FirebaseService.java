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
import java.util.List;
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

    public void sendMessage(MessagesDTO message) throws Exception {
        firebaseRepositoryDao.sendMessage(message);
    }

    public void createChatRoom(ChatRoomDTO chatRoom) {
        firebaseRepositoryDao.createChatRoom(chatRoom);
    }

    public ChatRoomDTO findChatRoom(String roomId) throws Exception {
        return firebaseRepositoryDao.getChatRoom(roomId);
    }

    public boolean joinRoom(ChatRoomDTO room, Member member) throws Exception {
        // 이미 참여한 사용자인지 확인
        if (room.getParticipants().containsKey(member.getId().toString())) {
            log.info("already participated");
            // enter 시 true -> false 로
            firebaseRepositoryDao.enterRoom(room, member.getId().toString());
            return false;
        }

        // 참여자가 아니라면 정원이 찼는지 확인
        if (room.getParticipants().size() == room.getMax()) {
            log.info("max");
            return true;
        }

        // 나잇대가 맞는지 확인
        if (room.getAvailableAgeRange().equals(member.getAge_range())) {
            log.info("join complete");
            firebaseRepositoryDao.join(room, member.getId());
            return false;
        }

        log.info("age not match require: {}, user: {}", room.getAvailableAgeRange(), member.getAge_range());
        return true;
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

                mailService.sendNotify(room.getRoomId(), receivers);
                room.setIsUpdated(false);
                firebaseRepositoryDao.updateRoomInfo(room);
            }
        }
    }
}