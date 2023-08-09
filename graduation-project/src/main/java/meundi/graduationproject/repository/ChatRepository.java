package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Chat;
import meundi.graduationproject.domain.ChatRecomment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final EntityManager em;
    private final CultureRepositoryUsingJPA CRJ;
    private final MemberRepositoryUsingJPA MRJ;

    public void save(Chat chat){
        em.persist(chat);
    }

    public void deleteChat(Chat chat){
        em.remove(chat);
    }
    public Chat findOne(Long id){
        return em.find(Chat.class, id);
    }

    //대댓글 관련 로직
    public void saveComment(ChatRecomment chatRecomment){
        em.persist(chatRecomment);
    }
    public void deleteComment(ChatRecomment chatRecomment){
        em.remove(chatRecomment);
    }
    public ChatRecomment findComment(Long id){
        return em.find(ChatRecomment.class, id);
    }
    public List<ChatRecomment> findChatRecomment(Chat chat){
        return em.createQuery("select sc from ChatRecomment sc where sc.chat= :chat", ChatRecomment.class)
                .setParameter("chat", chat)
                .getResultList();
    }
}
