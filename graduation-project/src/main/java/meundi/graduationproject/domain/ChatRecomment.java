package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class ChatRecomment {

    @Id
    @GeneratedValue
    @Column(name = "chat_recomment_id")
    private Long id;

    //채팅내용
    @NotBlank
    private String content;

    //채팅 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chatting_id")
    private Chat chat;

}
