package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.DTO.ChatRoomDTO;
import meundi.graduationproject.domain.DTO.MessagesDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.repository.FirebaseRepositoryDao;
import meundi.graduationproject.repository.MemberRepositoryUsingJPA;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseService {

    private final FirebaseRepositoryDao firebaseRepositoryDao;

    public List<MessagesDTO> getMessages() throws ExecutionException, InterruptedException {
        return firebaseRepositoryDao.getMessages();
    }

    public void insertMessage(String text) throws Exception {
        firebaseRepositoryDao.insertMessage(text);
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
}
