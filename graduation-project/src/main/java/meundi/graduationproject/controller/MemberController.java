package meundi.graduationproject.controller;

import meundi.graduationproject.domain.Member;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

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
        member.setPassword(form.getPassword());
        member.setNickName(form.getNickName());
        member.setSex(form.getSex());
        member.setAge(form.getAge());
        member.setDistrict(form.getDistrict());
        member.setFavoriteCategory(form.getFavoriteCategory());

        memberService.join(member);

        return "redirect:/";
    }
}
