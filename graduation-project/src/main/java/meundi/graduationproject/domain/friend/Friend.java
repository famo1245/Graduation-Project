package meundi.graduationproject.domain.friend;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.Member;
import org.hibernate.validator.constraints.Range;

@Entity
@Getter
public class Friend {

    @Id
    @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    /* 인원 수 */
    @Range(min = 2, max= 10)
    private int num;

    /* 작성자 */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    /* 작성일자 */
    private LocalDateTime dateTime;

    /* 검색 및 Create 용 문화 제목*/
    private String cultureTitle;

    /* 만나는 일자*/
//    @JsonFormat(shape = Shape.STRING, pattern = "yyyyMMdd, hh:mm")
    private String  meatTime;
    @ManyToOne
    @JoinColumn(name = "culture_id")
    private Culture culture;

    @OneToMany(mappedBy = "friend")
    private List<FriendComment> friendComments;

    @OneToMany(mappedBy = "friend")
    private List<CommentMember> commentMembers;

    public void CreatNewFriend(String title, String contents, int num, String cultureTitle,
        String meatTime) {
        this.title = title;
        this.contents = contents;
        this.num = num;
        this.cultureTitle = cultureTitle;
        this.meatTime = meatTime;
    }
}
