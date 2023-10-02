package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;
import meundi.graduationproject.domain.ReviewComment;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewCommentDTO {
    private Long id;
    private String content;
    private Long userId;
    private String nickname;
    private LocalDateTime createdDate;

    public void setReviewCommentDTO(ReviewComment c) {
        this.id = c.getId();
        this.content = c.getContent();
        this.userId = c.getMember().getId();
        this.nickname = c.getMember().getNickName();
    }
}
