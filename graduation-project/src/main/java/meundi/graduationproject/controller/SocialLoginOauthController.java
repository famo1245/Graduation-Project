//package meundi.graduationproject.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.PackagePrivate;
//import lombok.extern.slf4j.Slf4j;
//import meundi.graduationproject.domain.login.SocialLoginType;
//import meundi.graduationproject.service.OauthService;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@CrossOrigin
//@RequiredArgsConstructor
//@RequestMapping(value = "/auth")
//@Slf4j
//public class SocialLoginOauthController {
//    private final OauthService oauthService;
//
//    @GetMapping(value = "/{socialLoginType}")
//    public void socialLoginType(@PathVariable(name = "socialLoginType")SocialLoginType socialLoginType){
//        log.info(">>사용자로부터 SNS 로그인 요청을 받음 :: {} Social Login", socialLoginType);
//        oauthService.request(socialLoginType);
//        return;
//    }
//}
