package meundi.graduationproject.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.domain.ReviewComment;
import meundi.graduationproject.repository.ReviewRepository;
import meundi.graduationproject.repository.ReviewRepositoryUsingJPA;
import meundi.graduationproject.repository.ReviewSearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewRepositoryUsingJPA reviewRepositoryUsingJPA;

    @Value("${api.image.upload.url}")
    private String imageUrl;

    public Review insertReview(Review review, Member member) {
        member.plusTierScore(30);
        reviewRepository.save(review);
        return review;
    }

    public void save(Review r) {
        reviewRepository.save(r);
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
        List<Long> jimMember = one.getJimMember();
        /* 찜 유저에 있으면 리턴 */
        for (Long m : jimMember) {
            if (m.equals(member.getId())) {
                one.setJim(Math.max(one.getJim() - 1, 0));
                one.minusJimMember(member.getId());
                return;
            }
        }
        /* 짐멤버 추가 및 찜 +1 */
        member.plusTierScore(10);
        one.addJimMember(member.getId());
        one.setJim(one.getJim()+1);
    }

    public String saveReviewImage(MultipartFile image) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String currentDate = format.format(new Date());
        String fileName = UUID.randomUUID().toString();
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = "images/" + currentDate;
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        String fileExt = "";
        switch (image.getContentType()) {
            case "image/jpeg":
                fileExt = ".jpg";
                break;
            case "image/png":
                fileExt = ".png";
                break;
            case "image/gif":
                fileExt = ".gif";
                break;
        }

        fileName += fileExt;
        file = new File(absolutePath + path + "/" + fileName);
        image.transferTo(file);
        log.info("path={}",file.getAbsolutePath());
        return imageUrl + path + "/" + fileName;
    }
}
