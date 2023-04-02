package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    /** userId와 cultureID 일단 제외
     *  user 객체와 culture 객체로 제공해야함
     * */
    /*리뷰 고유 번호*/
    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    /*리뷰 작성 시간 */
    private LocalDateTime reviewDateTime;
    /*리뷰 내용*/
    private String reviewContents;
    /*리뷰 제목*/
    private String reviewTitle;
    /*평점: 1~5*/
    private int reviewGrade;
    /*userId for 어떤유저가 작성했는지 알기 위해*/
    private Long userId;
    /*cultureId for 어떤 문화에 대한 리뷰인지*/
    private Long cultureId;

}
