package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter @ToString
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
    private int jimCount;
    private List<Long> jimMember;
    private String nickname;
    private String main_img;

    public void setReviewDTO(Review review, String nickname, String main_img) {
        this.id = review.getId();
        this.reviewContents = review.getReviewContents();
        this.reviewGrade = review.getReviewGrade();
        this.reviewTitle = review.getReviewTitle();
        this.reviewDateTime = review.getReviewDateTime();
        this.nickname = nickname;
        this.main_img = main_img;
        this.jimCount = review.getJim();
        this.jimMember = new ArrayList<>();
        for (Member m : review.getJimMember()) {
            jimMember.add(m.getId());
        }
    }
}
