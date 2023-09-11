package meundi.graduationproject.domain.DTO.response;

import lombok.Getter;
import meundi.graduationproject.domain.Chat;
import meundi.graduationproject.domain.ChatRecomment;
import meundi.graduationproject.domain.Member;

@Getter
public class ChatResponseDTO {

    Chat chatDetail;
    Member sessionUser;
    ChatRecomment chatRecomment;

    public void creatChatResponseDTObyMember(Member member) {
        this.sessionUser = member;
    }

    public void creatChatResponseDTO(Chat chat, ChatRecomment chatRecomment) {
        this.chatDetail = chat;
        this.chatRecomment = chatRecomment;
    }
}
