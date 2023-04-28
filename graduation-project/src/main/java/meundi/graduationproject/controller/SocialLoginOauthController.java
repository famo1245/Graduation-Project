package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.login.SocialLoginType;
import meundi.graduationproject.service.MemberService;
import meundi.graduationproject.service.OauthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Slf4j
public class SocialLoginOauthController {

    private final OauthService oauthService;
    private final MemberService memberService;

    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
                                HttpServletResponse response) throws Exception {
        log.info("requested sns login typ :: {} Social Login", socialLoginType);
        String redirectURL = oauthService.request(socialLoginType);
        response.sendRedirect(redirectURL);
    }

    @GetMapping(value = "/{socialLoginType}/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           HttpSession session,
                           Model model,
                           @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        String accessToken = oauthService.requestAccessToekn(socialLoginType, code);
        HashMap<String, Object> userInfo = oauthService.getUserInfo(socialLoginType, accessToken);

        if (userInfo.get("id") != null) {
            if (memberService.findById((Long) userInfo.get("id")) == null) {
                model.addAttribute("id", userInfo.get("id"));
                model.addAttribute("email", userInfo.get("email"));
                model.addAttribute("gender", userInfo.get("gender"));
                model.addAttribute("age_range", userInfo.get("age_range"));
                return "/members/createMemberForm";
            }
            session.setAttribute("userId", userInfo.get("id"));
            session.setAttribute("access_Token", accessToken);
            session.setAttribute("type", socialLoginType);
            session.setAttribute("status", "true");
        }
        return "redirect:/";
    }

    @GetMapping("/logout/{socialLoginType}")
    public String logout(@PathVariable(name = "socialLoginType") SocialLoginType socialLoginType,
                         HttpSession session) {
        oauthService.logout(socialLoginType, (String)session.getAttribute("access_Token"));
        session.invalidate();
        return "redirect:/";
    }
}
