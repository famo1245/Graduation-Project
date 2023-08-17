package meundi.graduationproject.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Messages;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class FirebaseDao {

    private static final String COLLECTION_MESSAGE = "messages";
    private static final Firestore db = FirestoreClient.getFirestore();

    public List<Messages> getMessages() throws ExecutionException, InterruptedException {
        List<Messages> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_MESSAGE)
                .orderBy("created_at", Query.Direction.ASCENDING)
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Messages.class));
        }
        return list;
    }

    public void insertMessage(String text) throws Exception {
        Messages m = new Messages();
        m.setText(text);
        m.setCreated_at(new Date());
        db.collection(COLLECTION_MESSAGE).add(m);
    }
}
