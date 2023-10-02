package meundi.graduationproject.controller;


import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.DTO.ReviewCommentDTO;
import meundi.graduationproject.domain.DTO.ReviewDTO;
import meundi.graduationproject.domain.DTO.response.ReviewResponseDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.domain.ReviewComment;
import meundi.graduationproject.repository.ReviewSearch;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.MemberService;
import meundi.graduationproject.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final CultureService cultureService;
    private final MemberService memberService;

    @GetMapping /*home*/
    public ResponseEntity<Map<String, Object>> HomeReview() {
        List<Review> temp = reviewService.findReviewAll();
        Collections.reverse(temp);
        Map<String, Object> data = new HashMap<>();
        List<ReviewDTO> reviewAll = new ArrayList<>();
        for (Review r : temp) {
            ReviewDTO rdto = new ReviewDTO();
            rdto.setReviewDTO(r, r.getMember().getNickName(), r.getCulture().getMain_img());
            reviewAll.add(rdto);
        }
        data.put("reviews", reviewAll);
        return ResponseEntity.status(HttpStatus.OK).body(data);

    }

    @PostMapping/* search home */
    public ResponseEntity<Map<String, Object>> SearchReview(@RequestBody ReviewSearch reviewSearch,
        Model model)
        throws MalformedURLException {
        List<Review> reviewAll = reviewService.SearchReview(reviewSearch);
        Map<String, Object> message = new HashMap<>();
        message.put("data", reviewAll);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    @GetMapping("/reviewDetail/{review_id}") /*리뷰 하나를 클릭 시, 리뷰 자세히 보기*/
    public ResponseEntity<Map<String, Object>> reviewDetail(@PathVariable Long review_id,
        Model model, HttpSession session) {

        Review reviewDetail = reviewService.findOne(review_id);
        Member member;
        Map<String, Object> message = new HashMap<>();
        ReviewResponseDTO responseDTO = new ReviewResponseDTO();
        if (session != null && session.getAttribute("userId") != null) {
            member = memberService.findById((Long) session.getAttribute("userId"));
            responseDTO.setSessionUser(member);
        }
        responseDTO.setReviewDetail(reviewDetail);
        responseDTO.setReviewComment(new ReviewComment());

        message.put("data", responseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/reviewDetail/{review_id}")
    public ResponseEntity<Map<String, Object>> reviewAddComment(@RequestBody ReviewComment reviewComment,
                                                                @PathVariable Long review_id, @RequestParam Long userId) {
        Map<String, Object> message = new HashMap<>();
        // 리뷰 가져오기
        reviewComment.setReview(reviewService.findOne(review_id));

        // 댓글에 작성자 추가
        Member member = memberService.findById(userId);
        reviewComment.setMember(member);
        reviewService.insertReviewComment(reviewComment);
        ReviewCommentDTO result = new ReviewCommentDTO();
        result.setReviewCommentDTO(reviewComment);
        message.put("comment", result);
        return ResponseEntity.status(HttpStatus.OK).body(message);

    }

    @GetMapping("/reviewComment/{review_id}/{reviewComment_id}/delete")
    public ResponseEntity<Map<String, Object>> reviewDeleteComment(@PathVariable Long review_id,
        @PathVariable Long reviewComment_id, HttpSession session) {
        ReviewComment findComment = reviewService.findReviewComment(reviewComment_id);
        Map<String, Object> message = new HashMap<>();
        if (Objects.equals(findComment.getMember().getId(),
            (Long) session.getAttribute("userId"))) {
            reviewService.deleteReviewComment(findComment);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    // 리뷰 삭제
    @GetMapping("reviewDetail/{review_id}/delete")
    public ResponseEntity<Map<String, Object>> reviewDelete(@PathVariable Long review_id,
        HttpSession session) {
        Review review = reviewService.findOne(review_id);
        Map<String, Object> message = new HashMap<>();

        if (Objects.equals(review.getMember().getId(), (Long) session.getAttribute("userId"))) {
            reviewService.deleteReview(reviewService.findOne(review_id));
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /**
     * 리뷰 작성화면 service인 reviewWrite를 통해, 써진 reviewNote를 반환해야함 입력 -> 출력
     */
    @GetMapping("/reviewWrite")
    public ResponseEntity<Map<String, Object>> addReviewForm() {
        Map<String, Object> message = new HashMap<>();
        message.put("data", new Review());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    /* 내가 본 문화를 선택하는 것을 수정해야함
     * 수정 전: 문화 제목으로 검색하여 매치
     * 수정 후: 문화 제목으로 검색하여, 문화들을 띄워주고, 문화를 선택하는 방식
     * */
    @PostMapping("/reviewWrite")/*리뷰 작성시, 내용 넘겨주고, 작성된 화면으로 넘어감*/
    public ResponseEntity<Map<String, Object>> addReview(@RequestBody Map<String, Object> body) throws IOException {
        Review review = new Review();
        Map<String, Object> message = new HashMap<>();

        Long userId = Long.parseLong((String) body.get("userId"));
        review.setReviewTitle((String) body.get("reviewTitle"));
        review.setReviewContents((String) body.get("reviewContents"));
        review.setCultureTitle((String) body.get("cultureTitle"));
        review.setReviewGrade(Integer.parseInt((String) body.get("reviewGrade")));
        review.setReviewDateTime(LocalDateTime.now());
        Culture culture = cultureService.findOneByTitle(review.getCultureTitle())
            .get(); /* 문화 제목(입력)으로 문화 찾기*/
//      세션을 통해, 멤버 가져오기
        Member member = memberService.findById(userId);
        review.setMember(member);
        review.setCulture(culture); /*리뷰 객체에 문화 객체 넣기 */
        review.setJim(0);
        Review savedReview = reviewService.insertReview(review,member);
        message.put("data", savedReview);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    //  리뷰 수정
    @GetMapping("reviewDetail/{review_id}/edit")
    public ResponseEntity<Map<String, Object>> editReviewForm(
        @PathVariable("review_id") Long reviewId, HttpSession session) {
        Map<String, Object> message = new HashMap<>();
        // 세션이 없거나 세션 사용자와 리뷰 작성자가 다르면 거부 (URL 로 직접 접근시, 거부)
        if (session.getAttribute("userId") != null) {
            Member member = memberService.findById((Long) session.getAttribute("userId"));
            if (!Objects.equals(member.getId(),
                reviewService.findOne(reviewId).getMember().getId())) {
            }
        }
        Review review = reviewService.findOne(reviewId);
        message.put("data", review);
        return ResponseEntity.status(HttpStatus.OK).body(message);


    }

    @PostMapping("reviewDetail/{review_id}/edit")
    public ResponseEntity<Map<String, Object>> editReview(
        @Validated @RequestBody ReviewDTO review, BindingResult bindingResult,
        @PathVariable("review_id") Long reviewId) throws BindException {
        // 검증 로직
        if (!StringUtils.hasText(review.getReviewTitle())) {
            bindingResult.rejectValue("reviewTitle", "required");
        }
        if (!StringUtils.hasText(review.getReviewContents())) {
            bindingResult.rejectValue("reviewContents", "required");
        }

        /* 검증에 문제 발생 시, 다시 add */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        reviewService.updateReview(reviewId, review.getReviewTitle()
            , review.getReviewGrade(), review.getReviewContents());
        Map<String, Object> message = new HashMap<>();
        message.put("data", reviewService.findOne(reviewId));
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    /* 찜 API */
    @GetMapping("jim/{review_id}")
    public  ResponseEntity<Map<String, Object>> JimReview(@PathVariable("review_id") Long reviewId
    ,HttpSession session){
        Member member = null;
        if (session.getAttribute("userId") != null) {
            member = memberService.findById((Long) session.getAttribute("userId"));
        }
        reviewService.plusJimReview(member, reviewId);
        Map<String, Object> message = new HashMap<>();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}

