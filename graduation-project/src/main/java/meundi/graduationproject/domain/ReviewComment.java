package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime createdDate;
}
