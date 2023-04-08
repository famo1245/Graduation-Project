package meundi.graduationproject.service;


import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review insertReview(Review review) {
       reviewRepository.save(review);
        return review;
    }

    public void updateReview(Long id, String title, int grade, String content) {
        Review one = reviewRepository.findOne(id);
        one.setReviewTitle(title);
        one.setReviewGrade(grade);
        one.setReviewContents(content);
    }
    public List<Review> findReviewAll(){
        return reviewRepository.findAll();
    }
    public List<Review> findReviewSearch(String cultureTitle) {
        return reviewRepository.findByCultureTitle(cultureTitle);
    }
    public Review findOne(Long id) {
        return reviewRepository.findOne(id);
    }



}
