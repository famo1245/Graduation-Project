package meundi.graduationproject.controller;

import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.service.KaKaoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@Controller
public class HomeController {
    @Autowired
    private KaKaoAPI kakao;

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/home/member")
    public String home(@RequestParam("code") String code,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        String accessToken = kakao.getAccessToken(code);
        log.info("access token={}", accessToken);
        HashMap<String, Object> userInfo = kakao.getUserInfo(accessToken);
        log.info("userInfo={}", userInfo);

        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", accessToken);
            redirectAttributes.addAttribute("status", true);
        }

        return "redirect:/";
    }

    @RequestMapping("/members/login")
    public String login() {
        return "members/loginForm";
    }

    @RequestMapping("/members/logout")
    public String logout(HttpSession session) {
        kakao.kakaoLogout((String)session.getAttribute("access_Token"));
        session.removeAttribute("access_Token");
        session.removeAttribute("userId");
        return "redirect:/";
    }
}
