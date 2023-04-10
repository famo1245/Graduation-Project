package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.repository.MemberRepository;
import meundi.graduationproject.service.NaverAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class NaverLoginController {

    private final NaverAPI naverAPI;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/NAVER")
    public void socialLoginType(HttpServletResponse response) throws Exception{
        log.info("Naver Login");
        String redirectURL = naverAPI.getOauthRedirectURL();
        log.info("redirect url: {}", redirectURL);
        response.sendRedirect(redirectURL);
    }

    @GetMapping(value = "/naver/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpSession session,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        String access_token = naverAPI.requestAccessToken(code);
        log.info("access token: {}", access_token);
        HashMap<String, Object> userInfo = naverAPI.getUserInfo(access_token);
        log.info("userInfo={}", userInfo);

        if (userInfo.get("email") != null) {
            if(memberRepository.findById((Long) userInfo.get("id")) == null) {
                model.addAttribute("id", userInfo.get("id"));
                model.addAttribute("email", userInfo.get("email"));
                model.addAttribute("gender", userInfo.get("gender"));
                model.addAttribute("age_range", userInfo.get("age_range"));
                return "/members/createMemberForm";
            }
            session.setAttribute("userId", userInfo.get("id"));
            session.setAttribute("access_Token", access_token);
            redirectAttributes.addAttribute("status", true);
        }

        return "redirect:/";
    }
}
