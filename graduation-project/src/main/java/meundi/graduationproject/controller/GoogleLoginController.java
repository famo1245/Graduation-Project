package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.repository.MemberRepository;
import meundi.graduationproject.service.GoogleAPI;
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
public class GoogleLoginController {

    private final GoogleAPI googleAPI;
    private final MemberRepository memberRepository;

    /**
     * 사용자로부터 SNS 로그인 요청을 Social Login Type 을 받아 처리
     * @param response (GOOGLE, FACEBOOK, NAVER, KAKAO)
     */
    @GetMapping(value = "/GOOGLE")
    public void socialLoginType(HttpServletResponse response) throws Exception {
        log.info("Google login");
        String redirectURL = googleAPI.getOauthRedirectURL();
        response.sendRedirect(redirectURL);
    }

    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param SocialLoginType (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code API Server 로부터 넘어노는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 String 문자열 (access_token, refresh_token 등)
     */
    @GetMapping(value = "/google/callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpSession session,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        String accessToken = googleAPI.requestAccessToken(code);
        log.info("acesss_token: {}", accessToken);
        HashMap<String, Object> userInfo = googleAPI.getUserInfo(accessToken);
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
            redirectAttributes.addAttribute("status", true);
        }

        return "redirect:/";
    }
}
