package meundi.graduationproject.repository;

import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepositoryUsingJPA extends JpaRepository<Review,Long> {
    //select * from Review WHERE reviewTitle LIKE "%reviewTitle%"
    List<Review> findByReviewTitleContaining(String reviewTitle);
    //select * from Review WHERE Member LIKE "%Member%" equals 를 이용한 동등비교
    List<Review> findByMemberLike(Member member);
    //select * from Review WHERE Culture LIKE "%Culture%" equals 를 이용한 동등비교
    List<Review> findByCultureLike(Culture culture);
}
