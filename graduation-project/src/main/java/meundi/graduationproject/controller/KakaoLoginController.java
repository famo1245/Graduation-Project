package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.repository.MemberRepository;
import meundi.graduationproject.service.KaKaoAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class KakaoLoginController {

    private final KaKaoAPI kakao;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/KAKAO")
    public void socialLoginType(HttpServletResponse response) throws Exception {
        log.info("Kakao login");
        String redirectURL = kakao.getOauthRedirectUrl();
        response.sendRedirect(redirectURL);
    }

    @GetMapping("/kakao/callback")
    public String home(@RequestParam("code") String code,
                       HttpSession session,
                       Model model) {
        String accessToken = kakao.getAccessToken(code);
        log.info("access token={}", accessToken);
        HashMap<String, Object> userInfo = kakao.getUserInfo(accessToken);
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
            session.setAttribute("access_Token", accessToken);
            session.setAttribute("type", "KAKAO");
            session.setAttribute("status", "true");
        }

        return "redirect:/";
    }

    @RequestMapping("/logout/KAKAO")
    public String logout(HttpSession session) {
        kakao.logout((String)session.getAttribute("access_Token"));
        session.invalidate();
        return "redirect:/";
    }
}
