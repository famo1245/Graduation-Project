package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
public class ReviewComment {
    @Id
    @GeneratedValue
    @Column(name = "ReviewComment_id")
    private Long id;
    // 댓글
    @NotBlank
    private String content;
    // 댓글 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // 댓글 다는 해당 리뷰
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}