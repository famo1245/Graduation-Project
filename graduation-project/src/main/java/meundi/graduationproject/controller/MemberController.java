package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.DTO.MemberDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Tiers;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @ModelAttribute("districts")
    public List<String> districtName() {
        List<String> districts = new ArrayList<>();
        districts.add("종로구");
        return districts;
    }

    @GetMapping("/login")
    public String login() {
        return "members/loginForm";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(MemberForm form) {
        Member member = memberService.createMember(form);
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/info")
    public String myInfo(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        MemberDTO myInfo = memberService.research(userId);
        if(myInfo != null) {
            model.addAttribute("myInfo", myInfo);
        }

        return "/members/myInfo";
    }

    @GetMapping("/info/update")
    public String updateInfo(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        MemberDTO myInfo = memberService.research(userId);
        if(myInfo != null) {
            model.addAttribute("myInfo", myInfo);
        }
        return "/members/update-myInfo";
    }

    @PostMapping("/info/update")
    public String afterUpdate(MemberForm form, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        memberService.updateMember(userId, form);
        return "redirect:/members/info";
    }
}
