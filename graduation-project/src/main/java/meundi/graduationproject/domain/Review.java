package meundi.graduationproject.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    /** userId와 일단 제외
     *  user 객체로 제공해야함
     * */
    /*리뷰 고유 번호*/
    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    /*리뷰 작성 시간 */
    private LocalDateTime reviewDateTime;
    /*리뷰 내용*/
    @NotBlank
    private String reviewContents;
    /*리뷰 제목*/
    @NotBlank
    private String reviewTitle;
    /*평점: 1~5*/
    @NotNull
    @Range(min = 1, max=5)
    private int reviewGrade;
    /*userId for 어떤유저가 작성했는지 알기 위해*/
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    /*cultureId for 어떤 문화에 대한 리뷰인지*/
    @ManyToOne
    @JoinColumn(name = "culture_id")
    private Culture culture;

    @NotBlank
    private String cultureTitle;

}
