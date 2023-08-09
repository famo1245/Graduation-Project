package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Chat;
import meundi.graduationproject.domain.ChatRecomment;
import meundi.graduationproject.repository.ChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat insertChat(Chat chat){
        chatRepository.save(chat);
        return chat;
    }
    public void deleteChat(Chat chat){
        chatRepository.deleteChat(chat);
    }
    public Chat findOne(Long id){
        return chatRepository.findOne(id);
    }
    public ChatRecomment insertChatRecomment(ChatRecomment chatRecomment){
        chatRepository.saveComment(chatRecomment);
        return chatRecomment;
    }
    public void deleteChatRecomment(ChatRecomment chatRecomment){
        chatRepository.deleteComment(chatRecomment);
    }
    public ChatRecomment findChatRecomment(Long id){
        return chatRepository.findComment(id);
    }
}
