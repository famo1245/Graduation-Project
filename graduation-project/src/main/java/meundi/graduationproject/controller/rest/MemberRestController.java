package meundi.graduationproject.controller.rest;


import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.DTO.MemberForm;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login() {
        Map<String, Object> message = new HashMap<>();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/new")
    public ResponseEntity<Map<String, Object>> createForm(Model model) {
        Map<String, Object> message = new HashMap<>();
        message.put("data", new MemberForm());
        return ResponseEntity.status(HttpStatus.OK).body(message);

    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> create(MemberForm form, BindingResult bindingResult)
        throws BindException {
        if (!StringUtils.hasText(form.getNickName())) {
            bindingResult.rejectValue("nickName", "required");
        }
        if (!StringUtils.hasText(form.getDistrict())) {
            bindingResult.rejectValue("district", "required");
        }
        if (memberService.validateDuplicatedNickName(form.getNickName())) {
            bindingResult.rejectValue("nickName", "duplicated");
        }

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        Map<String, Object> message = new HashMap<>();
        Member member = memberService.createMember(form);
        memberService.join(member);
        message.put("data", form);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> myInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        MemberForm myInfo = memberService.research(userId);
        Map<String, Object> message = new HashMap<>();
        if (myInfo != null) {
            message.put("data", myInfo);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/info/update")
    public ResponseEntity<Map<String, Object>> myInfoUpdate(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        MemberForm myInfo = memberService.research(userId);
        Map<String, Object> message = new HashMap<>();
        if (myInfo != null) {
            message.put("data", myInfo);
        }
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    //memberForm 으로 변환 해야함
    @PostMapping("/info/update")
    public ResponseEntity<Map<String, Object>> afterUpdate(MemberForm form,
        BindingResult bindingResult, HttpSession session) throws BindException {
        Long userId = (Long) session.getAttribute("userId");
        String currentNickName = memberService.findById(userId).getNickName();

        //검증 로직
        if (!StringUtils.hasText(form.getNickName())) {
            bindingResult.rejectValue("nickName", "required");
        }
        if (!StringUtils.hasText(form.getDistrict())) {
            bindingResult.rejectValue("district", "required");
        }
        if (!form.getNickName().equals(currentNickName)) {
            if (memberService.validateDuplicatedNickName(form.getNickName())) {
                bindingResult.rejectValue("nickName", "duplicated");
            }
        }
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        Map<String, Object> message = new HashMap<>();
        memberService.updateMember(userId, form);
        message.put("data", form);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


}
