package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.service.NaverAPI;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class NaverLoginController {

    private final NaverAPI naverAPI;

    @GetMapping(value = "/NAVER")
    public void socialLoginType(HttpServletResponse response) throws Exception{
        log.info("Naver Login");
        String redirectURL = naverAPI.getOauthRedirectURL();
        log.info("redirect url: {}", redirectURL);
        response.sendRedirect(redirectURL);
    }

    @GetMapping(value = "/naver/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code :: {}", code);
        return naverAPI.requestAccessToken(code, state);
    }
}
