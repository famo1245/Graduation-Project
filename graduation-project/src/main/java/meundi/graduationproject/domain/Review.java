package meundi.graduationproject.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.tomcat.util.buf.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Review {

    /*리뷰 고유 번호*/
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    /*리뷰 작성 시간 */
    private LocalDateTime reviewDateTime;

    /*리뷰 내용*/
    @NotBlank
    @Column(length = 800)
    private String reviewContents;

    /*리뷰 제목*/
    @NotBlank
    private String reviewTitle;

    /*평점: 0~5*/
    @NotNull
    @Range(min = 0, max = 5)
    private int reviewGrade;
    /* 리뷰 찜 */
    private Integer jim;

    /* 찜한 유저 */
    @Column(length = 1000)
    private String jimMember;

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

    @Column(length = 500)
    private String image1Url;

    @Column(length = 500)
    private String image2Url;

    @OneToMany(mappedBy = "review")
    private List<ReviewComment>  reviewComments;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return getReviewGrade() == review.getReviewGrade() && Objects.equals(getId(), review.getId())
                && Objects.equals(getReviewDateTime(), review.getReviewDateTime())
                && Objects.equals(getReviewContents(), review.getReviewContents())
                && Objects.equals(getReviewTitle(), review.getReviewTitle())
                && Objects.equals(getCulture(), review.getCulture())
                && Objects.equals(getCultureTitle(), review.getCultureTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReviewDateTime(), getReviewContents(),
                getReviewTitle(), getReviewGrade(), getMember(), getCulture(), getCultureTitle());
    }

    public void updateReview(String reviewTitle, int reviewGrade, String reviewContents) {
        this.reviewTitle = reviewTitle;
        this.reviewGrade = reviewGrade;
        this.reviewContents = reviewContents;
    }

    public List<Long> getJimMember() {
        List<Long> jimMember = new ArrayList<>();
        if (this.jimMember == null || this.jimMember.isEmpty()) {
            return jimMember;
        }

        String[] temp = this.jimMember.split(", ");
        for (String s : temp) {
            jimMember.add(Long.parseLong(s));
        }
        return jimMember;
    }

    public void addJimMember(Long id) {
        List<Long> likeMember = getJimMember();
        likeMember.add(id);
        String result = "";
        for (int i=0; i<likeMember.size(); i++) {
            result += likeMember.get(i).toString();

            if (i == likeMember.size() - 1) {
                continue;
            }

            result += ", ";
        }

        this.jimMember = result;
    }

    public void minusJimMember(Long id) {
        List<Long> likeMember = getJimMember();
        likeMember.remove(id);
        String result = "";
        for (int i=0; i<likeMember.size(); i++) {
            result += likeMember.get(i).toString();

            if (i == likeMember.size() - 1) {
                continue;
            }

            result += ", ";
        }

        this.jimMember = result;
    }
}
