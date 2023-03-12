package meundi.graduationproject.controller;

import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.service.KaKaoAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@Controller
public class HomeController {
    @Autowired
    private KaKaoAPI kakao;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/member/home")
    public String home(@RequestParam("code") String code, HttpSession session) {
        String accessToken = kakao.getAccessToken(code);
        HashMap<String, Object> userInfo = kakao.getUserInfo(accessToken);
        log.info("userInfo={}", userInfo);

        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", accessToken);
        }

        return "redirect:/";
    }
}
