package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.domain.ReviewComment;

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
    private String cultureTitle;
    private int jimCount;
    private List<Long> jimMember;
    private String nickname;
    private String main_img;
    private List<ReviewCommentDTO> reviewComments;
    private Long culture_id;
    private String image1Url;
    private String image2Url;

    public void setReviewDTO(Review review, String nickname, String main_img) {
        this.id = review.getId();
        this.reviewContents = review.getReviewContents();
        this.reviewGrade = review.getReviewGrade();
        this.reviewTitle = review.getReviewTitle();
        this.reviewDateTime = review.getReviewDateTime();
        this.nickname = nickname;
        this.main_img = main_img;
        this.jimCount = review.getJim();
        this.jimMember = review.getJimMember();
        this.reviewComments = new ArrayList<>();
        for (ReviewComment c : review.getReviewComments()) {
            ReviewCommentDTO temp = new ReviewCommentDTO();
            temp.setReviewCommentDTO(c);
            reviewComments.add(temp);
        }
        this.culture_id = review.getCulture().getId();
        this.cultureTitle = review.getCulture().getTitle();
        this.image1Url = review.getImage1Url();
        this.image2Url = review.getImage2Url();
    }
}
