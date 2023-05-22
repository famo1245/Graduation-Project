package meundi.graduationproject.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.domain.ReviewComment;
import meundi.graduationproject.repository.ReviewSearch;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.MemberService;
import meundi.graduationproject.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final CultureService cultureService;
    private final MemberService memberService;

    @GetMapping /*home*/
    public String SearchReview(@ModelAttribute("reviewSearch") ReviewSearch reviewSearch, Model model) throws MalformedURLException {
//        테스트용 문화
//        Culture testCulture = new Culture();
//        testCulture.InsertCultureFromJson("test"," ","http://www.gbcf.or.kr/load.asp?subPage=110.View&page=1&idx=114",
//                "\thttps://culture.seoul.go.kr/cmmn/file/getImage.do?atchFileId=26774fbc85b14bc29865f3274b0951ab&thumb=Y",
//                "강북구","2022-05-12","2022-04-25","뮤지컬/오페라"," ","강북문화예술회관 대공연장");
//        cultureService.insertCulture(testCulture);
//        // 테스트용 리뷰
//        Review testReview = new Review();
//        testReview.setReviewTitle(String.valueOf(LocalDateTime.now()));
//        testReview.setReviewContents("리뷰 테스트 내용");
//        testReview.setReviewTitle("리뷰 테스트 제목 ");
//        testReview.setReviewGrade(3);
//        testReview.setCulture(testCulture);
//        testReview.setCultureTitle(testCulture.getTitle());
//        reviewService.insertReview(testReview);

        List<Review> reviewAll = reviewService.SearchReview(reviewSearch);
        model.addAttribute("reviewAll", reviewAll);
        return "review/reviewHome";
    }


    @GetMapping("/reviewDetail/{review_id}") /*리뷰 하나를 클릭 시, 리뷰 자세히 보기*/
    public String reviewDetail(@PathVariable Long review_id, Model model,HttpSession session) {
        Review reviewDetail = reviewService.findOne(review_id);
        Member member;
        if(session != null && session.getAttribute("userId") != null){
            member = memberService.findById((Long)session.getAttribute("userId"));
            model.addAttribute("sessionUser",member);
        }
        model.addAttribute("reviewDetail", reviewDetail);
        model.addAttribute("reviewComment",new ReviewComment());
        return "review/reviewDetail";
    }
    @PostMapping("/reviewDetail/{review_id}")
    public String reviewAddComment(@Validated @ModelAttribute ReviewComment reviewComment
            ,@PathVariable Long review_id, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session){
        redirectAttributes.addAttribute("reviewId",review_id);
        /* 이를 지나치지 않고 에러를 띄움 왜지*/
        if(bindingResult.hasErrors()){
            return "redirect:/review/reviewDetail/{reviewId}";
        }
        // 리뷰 가져오기
        reviewComment.setReview(reviewService.findOne(review_id));
        // 댓글에 작성자 추가
        Member member = memberService.findById((Long)session.getAttribute("userId"));
        reviewComment.setMember(member);
        reviewService.insertReviewComment(reviewComment);
        return "redirect:/review/reviewDetail/{reviewId}";
    }

    @GetMapping("/reviewComment/{review_id}/{reviewComment_id}/delete")
    public String reviewDeleteComment(@PathVariable Long review_id,@PathVariable Long reviewComment_id,RedirectAttributes redirectAttributes,HttpSession session){
        ReviewComment findComment = reviewService.findReviewComment(reviewComment_id);
        log.info("댓글 작성자: {}", findComment.getMember().getId());
        log.info("세션 {}", (Long)session.getAttribute("userId"));
        if(Objects.equals(findComment.getMember().getId(), (Long) session.getAttribute("userId"))){
            log.info("리뷰 댓글 삭제 성공");
            reviewService.deleteReviewComment(findComment);
        }
        redirectAttributes.addAttribute("reviewId",review_id);
        return "redirect:/review/reviewDetail/{reviewId}";
    }
    // 리뷰 삭제
    @GetMapping("reviewDetail/{review_id}/delete")
    public String reviewDelete(@PathVariable Long review_id,HttpSession session){
        Review review = reviewService.findOne(review_id);
        log.info("리뷰 작성자: {}", review.getMember().getId());
        log.info("세션 {}", (Long)session.getAttribute("userId"));
        if(Objects.equals(review.getMember().getId(), (Long) session.getAttribute("userId"))){
            log.info("리뷰 삭제 성공");
            reviewService.deleteReview(reviewService.findOne(review_id));
        }
        return "redirect:/review";
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
    public String addReview(@Validated @ModelAttribute Review review, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
        /* 같은 문화 제목이 없을때, 오류*/
        if (cultureService.findOneByTitle(review.getCultureTitle()).isEmpty()) {
            bindingResult.reject("NoCulture");
        }
        /* 검증에 문제 발생 시, 다시 add */
        if (bindingResult.hasErrors()) {
            return "/review/addReview";
        }

        review.setReviewDateTime(LocalDateTime.now());
        Culture culture = cultureService.findOneByTitle(review.getCultureTitle()).get(); /* 문화 제목(입력)으로 문화 찾기*/
//      세션을 통해, 멤버 가져오기
        Member member = memberService.findById((Long)session.getAttribute("userId"));
        review.setMember(member);
        review.setCulture(culture); /*리뷰 객체에 문화 객체 넣기 */
        Review savedReview = reviewService.insertReview(review);
        redirectAttributes.addAttribute("reviewId", savedReview.getId());
        // 이거 뭔지 설명좀 -> 저장 후 넘어간 리뷰 상세화면인지, 그냥 홈화면에서 들어간 리뷰 상세화면인지를 나타내기 위한 status
        redirectAttributes.addAttribute("status", true);
        /*
         * model에 담긴 review 내용을 ReviewNote에 넣기
         * */
        return "redirect:/review/reviewDetail/{reviewId}";/*review_id를 통해, detail 열기*/
    }

//  리뷰 수정
    @GetMapping("reviewDetail/{review_id}/edit")
    public String editReviewForm(@PathVariable("review_id") Long reviewId, Model model,HttpSession session) {
        // 세션이 없거나 세션 사용자와 리뷰 작성자가 다르면 거부 (URL 로 직접 접근시, 거부)
        if (session.getAttribute("userId") != null){
            Member member = memberService.findById((Long)session.getAttribute("userId"));
            if(!Objects.equals(member.getId(), reviewService.findOne(reviewId).getMember().getId())){
                return "redirect:/reviewDetail/{review_id}";
            }
        }
        else {
            return "redirect:/reviewDetail/{review_id}";
        }
        Review review = reviewService.findOne(reviewId);
        model.addAttribute("review", review);
        return "review/editReview";
    }

    @PostMapping("reviewDetail/{review_id}/edit")
    public String editReview(@Validated @ModelAttribute Review review, BindingResult bindingResult,@PathVariable("review_id")Long reviewId) {
        /* 같은 문화 제목이 없을때, 오류*/
        if (cultureService.findOneByTitle(review.getCultureTitle()).isEmpty()) {
            bindingResult.reject("NoCulture");
        }
        /* 검증에 문제 발생 시, 다시 add */
        if (bindingResult.hasErrors()) {
            return "review/editReview";
        }
        reviewService.updateReview(reviewId, review.getCultureTitle()
                , review.getReviewGrade(), review.getReviewContents());

        return "redirect:/review";
    }


}

