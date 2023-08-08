package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Chat {

    //채팅 고유번호
    @Id
    @GeneratedValue
    @Column(name = "chatting_id")
    private Long id;

    //채팅 작성시간
    private LocalDateTime chattingDateTime;

    //채팅 내용
    @NotBlank
    private String chattingContents;

    @ManyToOne
    @JoinColumn(name = "culture_id")
    private Culture culture;
    @NotBlank
    private String cultureTitle;
    @OneToMany(mappedBy = "chat")
    private List<ChatRecomment> chatRecoments;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
