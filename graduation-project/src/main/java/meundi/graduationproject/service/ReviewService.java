package meundi.graduationproject.service;


import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.domain.ReviewComment;
import meundi.graduationproject.repository.ReviewRepository;
import meundi.graduationproject.repository.ReviewRepositoryUsingJPA;
import meundi.graduationproject.repository.ReviewSearch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewRepositoryUsingJPA reviewRepositoryUsingJPA;

    public Review insertReview(Review review, Member member) {
        member.plusTierScore(30);
        reviewRepository.save(review);
        return review;
    }

    public void deleteReview(Review review){
        reviewRepository.deleteReview(review);
    }

    public void updateReview(Long id, String title, int grade, String content) {
        Review find = reviewRepository.findOne(id);
        find.updateReview(title, grade, content);
    }

    public List<Review> findReviewAll(){
        return reviewRepositoryUsingJPA.findAll();
    }

    public List<Review> findReviewSearch(String cultureTitle) {
        return reviewRepository.findByCultureTitle(cultureTitle);
    }

    public Review findOne(Long id) {
        return reviewRepository.findOne(id);
    }

    public List<Review> SearchReview(ReviewSearch reviewSearch) {
        return reviewRepository.SearchReviewTotal(reviewSearch);
    }

    public ReviewComment insertReviewComment(ReviewComment reviewComment){
        reviewRepository.saveComment(reviewComment);
        return  reviewComment;
    }

    public void deleteReviewComment(ReviewComment reviewComment){
        reviewRepository.deleteComment(reviewComment);
    }

    public void editReviewComment(Long id,String content){
        ReviewComment comment = reviewRepository.findComment(id);
        comment.updateReviewComment(content);
    }

    public ReviewComment findReviewComment(Long id){
        return reviewRepository.findComment(id);
    }

    public void plusJimReview(Member member, Long review_id){
        Review one = reviewRepository.findOne(review_id);
        List<Member> jimMember = one.getJimMember();
        /* 찜 유저에 있으면 리턴 */
        for (Member member1 : jimMember) {
            if (member1.equals(member)) {
                return;
            }
        }
        /* 짐멤버 추가 및 찜 +1 */
        member.plusTierScore(10);
        jimMember.add(member);
        one.getJimMember().add(member);
        one.setJim(one.getJim()+1);
        return;
    }
}
