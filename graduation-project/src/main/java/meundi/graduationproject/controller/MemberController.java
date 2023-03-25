package meundi.graduationproject.controller;

import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Tiers;
import meundi.graduationproject.repository.MemberRepository;
import meundi.graduationproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberController(MemberService memberService, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/loginForm";
    }

    @PostMapping("/members/new")
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

    @GetMapping("/members/info")
    public String myInfo(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        Optional<Member> findMember = memberRepository.findById(userId);
        if(findMember.isPresent()) {
            Member myInfo = findMember.get();
            model.addAttribute("myInfo", myInfo);
        }

        return "/members/myInfo";
    }
}
