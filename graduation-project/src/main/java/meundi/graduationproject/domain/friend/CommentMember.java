package meundi.graduationproject.domain.friend;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class CommentMember {

    @Id
    @GeneratedValue
    @Column(name = "commentMember_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Friend friend;

    @ManyToOne
    @JoinColumn(name = "friendComment_id")
    private FriendComment friendComment;

}
