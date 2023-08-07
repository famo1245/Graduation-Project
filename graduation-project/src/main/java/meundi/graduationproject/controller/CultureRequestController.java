package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.*;
import meundi.graduationproject.domain.DTO.CultureRequestDTO;
import meundi.graduationproject.domain.DTO.ReviewDTO;
import meundi.graduationproject.service.CultureRequestService;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/culturerequest")
public class CultureRequestController {

    private final MemberService memberService;
    private final CultureRequestService cultureRequestService;


    @GetMapping("/list")
    public String requestList(Model model) {
        List<CultureRequest> requestList = cultureRequestService.findRequestAll();
        model.addAttribute("requestList", requestList);

        return "cultureRequest/requestList";
    }

    @GetMapping("/requestDetail/{request_id}") /*리뷰 하나를 클릭 시, 리뷰 자세히 보기*/
    public String requestDetail(@PathVariable Long request_id, Model model,HttpSession session) {

        CultureRequest requestDetail = cultureRequestService.findOne(request_id);
        Member member;

        if(session != null && session.getAttribute("userId") != null){
            member = memberService.findById((Long)session.getAttribute("userId"));
            model.addAttribute("sessionUser",member);
        }

        model.addAttribute("requestDetail", requestDetail);

        return "cultureRequest/requestDetail";
    }

    @GetMapping("/requestDetail/{request_id}/delete")
    public String requestDelete(@PathVariable Long request_id,HttpSession session){
        CultureRequest request = cultureRequestService.findOne(request_id);

        log.info("문화요청 작성자: {}", request.getMember().getId());
        log.info("세션 {}", session.getAttribute("userId"));

        if(Objects.equals(request.getMember().getId(), (Long) session.getAttribute("userId"))){
            log.info("Comment deleted Id:{}", request_id);
            cultureRequestService.deleteCultureRequest(cultureRequestService.findOne(request_id));
        }

        return "redirect:/culturerequest/list";
    }

    @GetMapping("/write")
    public String requestAddForm(Model model) {
        model.addAttribute("request", new CultureRequest());
        return "cultureRequest/addRequest";
    }

    @PostMapping ("/write")
    public String addRequest(@Validated @ModelAttribute CultureRequest cultureRequest, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes, HttpSession session) throws IOException{

        if (bindingResult.hasErrors()) {
            return "forward:/culturerequest/write";
        }
        cultureRequest.setRequestDateTime(LocalDateTime.now());
        Member member = memberService.findById((Long)session.getAttribute("userId"));
        cultureRequest.setMember(member);
        CultureRequest savedRequest = cultureRequestService.insertCultureRequest(cultureRequest);
//        redirectAttributes.addAttribute("requestId", savedRequest.getId());
//        redirectAttributes.addAttribute("status", true);

        return "redirect:/culturerequest/list";

    }

    @GetMapping("requestDetail/{request_id}/edit")
    public String editRequestForm(@PathVariable("request_id") Long requestId, Model model,HttpSession session) {
        // 세션이 없거나 세션 사용자와 리뷰 작성자가 다르면 거부 (URL 로 직접 접근시, 거부)
        if (session.getAttribute("userId") != null){
            Member member = memberService.findById((Long)session.getAttribute("userId"));
            if(!Objects.equals(member.getId(), cultureRequestService.findOne(requestId).getMember().getId())){
                return "redirect:/culturerequest/requestDetail/{request_id}";
            }
        }
        else {
            return "redirect:/culturerequest/requestDetail/{request_id}";
        }
        CultureRequest request = cultureRequestService.findOne(requestId);
        log.info("request={}", request.getId());
        model.addAttribute("request", request);
        return "cultureRequest/editRequest";
    }

    @PostMapping("requestDetail/{request_id}/edit")
    public String editRequest(@Validated @ModelAttribute CultureRequestDTO request, BindingResult bindingResult,
                              @PathVariable("request_id")Long requestId) {
        // 검증 로직
        if (!StringUtils.hasText(request.getTitle())) {
            bindingResult.rejectValue("title", "required");
        }
        if (!StringUtils.hasText(request.getContents())) {
            bindingResult.rejectValue("requestContents", "required");
        }

        /* 검증에 문제 발생 시, 다시 add */
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "cultureRequest/editRequest";
        }
        cultureRequestService.updateCultureRequest(requestId, request.getTitle(), request.getContents());

        return "redirect:/culturerequest/requestDetail/{request_id}";
    }
}
