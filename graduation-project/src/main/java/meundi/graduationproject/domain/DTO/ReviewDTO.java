package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class ReviewDTO {

    private Long id;
    /*리뷰 작성 시간 */
    private LocalDateTime reviewDateTime;
    /*리뷰 내용*/
    private String reviewContents;
    /*리뷰 제목*/
    private String reviewTitle;
    /*평점: 0~5*/
    private int reviewGrade;

}
