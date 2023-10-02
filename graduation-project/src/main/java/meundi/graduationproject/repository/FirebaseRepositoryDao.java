package meundi.graduationproject.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.DTO.ChatRoomDTO;
import meundi.graduationproject.domain.DTO.MessagesDTO;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class FirebaseRepositoryDao {

    private static final String COLLECTION_MESSAGE = "messages";
    private static final String COLLECTION_CHAT_ROOM = "chat-rooms";
    private static final Firestore db = FirestoreClient.getFirestore();

    public List<MessagesDTO> getMessages(String roomId) throws ExecutionException, InterruptedException {
        List<MessagesDTO> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_MESSAGE)
                .whereEqualTo("roomId", roomId)
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

    public List<ChatRoomDTO> getChatRooms() throws Exception {
        List<ChatRoomDTO> rooms = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_CHAT_ROOM)
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            rooms.add(document.toObject(ChatRoomDTO.class));
        }

        Collections.reverse(rooms);
        return rooms;
    }

    public void join(ChatRoomDTO room, Long id) throws Exception {
        room.addParticipants(id);
        updateRoomInfo(room);
    }

    public void createChatRoom(ChatRoomDTO chatRoomDTO) {
        db.collection(COLLECTION_CHAT_ROOM).add(chatRoomDTO);
    }

    public void sendMessage(MessagesDTO message) throws Exception {
        db.collection(COLLECTION_MESSAGE).add(message);
        log.info("message send complete");
        ChatRoomDTO currentRoom = getChatRoom(message.getRoomId());
        currentRoom.setIsUpdated(true);
        for (String key : currentRoom.getParticipants().keySet()) {
            if (!key.equals(message.getAuthor().toString())) {
                currentRoom.getParticipants().put(key, true);
            }
        }

        updateRoomInfo(currentRoom);
        log.info("update room {}: complete by new message", message.getRoomId());
    }

    public void enterRoom(ChatRoomDTO room, String userId) throws Exception {
        room.getParticipants().put(userId, false);
        updateRoomInfo(room);
    }

    public void updateRoomInfo(ChatRoomDTO room) throws Exception {
        String roomId = room.getRoomId();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_CHAT_ROOM)
                .whereEqualTo("roomId", roomId)
                .get();
        String firebaseId = future.get().getDocuments().get(0).getId();
        db.collection(COLLECTION_CHAT_ROOM)
                .document(firebaseId).set(room, SetOptions.merge());
    }
}