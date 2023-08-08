package meundi.graduationproject.domain.friend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import meundi.graduationproject.domain.Member;


@Entity
@Getter
public class FriendComment {
    @Id
    @GeneratedValue
    @Column(name = "friendComment_id")
    private Long id;
    // 댓글
    @NotBlank
    private String content;
    // 댓글 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // 댓글 다는 해당 친구
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Friend friend;
}
