package meundi.graduationproject.controller;


import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final CultureService cultureService;

    @GetMapping /*home*/
    public String reviewHome(Model model) {
        List<Review> reviewAll = reviewService.findReviewAll();
        model.addAttribute("reviewAll", reviewAll);
        return "review/reviewHome";
    }


    @GetMapping("/reviewDetail/{review_id}") /*리뷰 하나를 클릭 시, 리뷰 자세히 보기*/
    public String reviewDetail(@PathVariable Long review_id, Model model) {
        Review reviewDetail = reviewService.findOne(review_id);
        model.addAttribute("reviewDetail", reviewDetail);
        return "review/reviewDetail";
    }

    @GetMapping("/reviewWrite")/*리뷰 작성화면*/
    public String addReviewForm(Model model) {
        /**
         * service인 reviewWrite를 통해, 써진 reviewNote를 반환해야함
        * 입력 -> 출력
         * */
        model.addAttribute("review", new Review());
        return "review/addReview";
    }

    /* 내가 본 문화를 선택하는 것을 수정해야함
    * 수정 전: 문화 제목으로 검색하여 매치
    * 수정 후: 문화 제목으로 검색하여, 문화들을 띄워주고, 문화를 선택하는 방식
    * */
    @PostMapping("/reviewWrite")/*리뷰 작성시, 내용 넘겨주고, 작성된 화면으로 넘어감*/
    public String addReview(@Validated @ModelAttribute Review review, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        /* 검증에 문제 발생 시, 다시 add */
        if (bindingResult.hasErrors()) {
            return "/review/addReview";
        }

        review.setReviewDateTime(LocalDateTime.now());

        Culture culture = cultureService.findOneByTitle(review.getCultureTitle()); /* 문화 제목(입력)으로 문화 찾기*/
        review.setCulture(culture); /*리뷰 객체에 문화 객체 넣기 */

        Review savedReview = reviewService.insertReview(review);
        redirectAttributes.addAttribute("reviewId", savedReview.getId());
        // 이거 뭔지 설명좀
        redirectAttributes.addAttribute("status", true);
        /*
         * model에 담긴 review 내용을 ReviewNote에 넣기
         * */
        return "redirect:/review/reviewDetail/{reviewId}";/*review_id를 통해, detail 열기*/
    }
}

