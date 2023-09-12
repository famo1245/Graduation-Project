package meundi.graduationproject.domain.DTO.response;

import lombok.Getter;
import lombok.Setter;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.domain.ReviewComment;

@Getter
@Setter
public class ReviewResponseDTO {
    Member sessionUser;
    Review reviewDetail;
    ReviewComment reviewComment;

}
