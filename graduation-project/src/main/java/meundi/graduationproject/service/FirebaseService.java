package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Messages;
import meundi.graduationproject.repository.FirebaseDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FirebaseService {

    private final FirebaseDao firebaseDao;

    public List<Messages> getMessages() throws ExecutionException, InterruptedException {
        return firebaseDao.getMessages();
    }

    public void sendMessage(Long userId, String roomId, String text) {
        firebaseDao.insertMessage(text);
    }
}
