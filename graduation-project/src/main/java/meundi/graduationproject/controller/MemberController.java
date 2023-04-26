package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Tiers;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/new")
    public String createForm() {
        return "members/loginForm";
    }

    @PostMapping("/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setId(form.getId());
        member.setNickName(form.getNickName());
        member.setGender(form.getGender());
        member.setDistrict(form.getDistrict());
        member.setAge_range(form.getAge_range());
        member.setEmail(form.getEmail());
        member.setFavoriteCategory(form.getFavoriteCategory());
        member.setTiers(Tiers.BRONZE);  //초기 티어는 브론즈

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/info")
    public String myInfo(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Member myInfo = memberService.findById(userId);
        if(myInfo != null) {
            model.addAttribute("myInfo", myInfo);
        }

        return "/members/myInfo";
    }
}
