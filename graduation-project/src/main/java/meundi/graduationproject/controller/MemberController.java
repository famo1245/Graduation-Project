package meundi.graduationproject.controller;

import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Tiers;
import meundi.graduationproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
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
}
