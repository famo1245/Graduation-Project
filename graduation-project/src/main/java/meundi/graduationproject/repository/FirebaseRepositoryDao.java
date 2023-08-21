package meundi.graduationproject.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.DTO.ChatRoomDTO;
import meundi.graduationproject.domain.DTO.MessagesDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class FirebaseRepositoryDao {

    private static final String COLLECTION_MESSAGE = "messages";
    private static final String COLLECTION_CHAT_ROOM = "chat-rooms";
    private static final Firestore db = FirestoreClient.getFirestore();

    public List<MessagesDTO> getMessages() throws ExecutionException, InterruptedException {
        List<MessagesDTO> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_MESSAGE)
                .orderBy("created_at", Query.Direction.ASCENDING)
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(MessagesDTO.class));
        }
        return list;
    }

    public ChatRoomDTO getChatRoom(String roomId) throws Exception {
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_CHAT_ROOM)
                .whereEqualTo("roomId", roomId)
                .get();
        QueryDocumentSnapshot document = future.get().getDocuments().get(0);
        return document.toObject(ChatRoomDTO.class);
    }

    public void join(ChatRoomDTO room, Long id) throws Exception {
        room.addParticipants(id);
        String roomId = room.getRoomId();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_CHAT_ROOM)
                .whereEqualTo("roomId", roomId)
                .get();
        String firebaseId = future.get().getDocuments().get(0).getId();
        db.collection(COLLECTION_CHAT_ROOM)
                .document(firebaseId).set(room, SetOptions.merge());
    }

    public void createChatRoom(ChatRoomDTO chatRoomDTO) {
        db.collection(COLLECTION_CHAT_ROOM).add(chatRoomDTO);
    }

    public void insertMessage(String text) throws Exception {
        MessagesDTO m = new MessagesDTO();
        m.setText(text);
        m.setCreated_at(new Date());
        db.collection(COLLECTION_MESSAGE).add(m);
    }
}
