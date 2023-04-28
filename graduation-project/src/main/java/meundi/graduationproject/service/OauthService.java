package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.login.SocialLoginType;
import meundi.graduationproject.service.social.GoogleOauth;
import meundi.graduationproject.service.social.KakaoOauth;
import meundi.graduationproject.service.social.NaverOauth;
import meundi.graduationproject.service.social.SocialLoginOauth;
import org.springframework.stereotype.Service;


import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final GoogleOauth googleOauth;
    private final KakaoOauth kakaoOauth;
    private final NaverOauth naverOauth;
    private SocialLoginOauth socialLoginOauth;

    //switch 통해서 소셜로그인 방식 선택
    public String request(SocialLoginType socialLoginType){
        find(socialLoginType);
        return socialLoginOauth.getOauthRedirectURL();
    }

    public String requestAccessToekn(SocialLoginType socialLoginType, String code) {
        find(socialLoginType);
        return socialLoginOauth.requestAccessToken(code);
    }

    public void logout(SocialLoginType socialLoginType, String access_token) {
        find(socialLoginType);
        socialLoginOauth.logout(access_token);
    }

    public HashMap<String, Object> getUserInfo(SocialLoginType socialLoginType, String accessToken) {
        find(socialLoginType);
        return socialLoginOauth.getUserInfo(accessToken);
    }

    private void find(SocialLoginType socialLoginType) {
        switch (socialLoginType) {
            case GOOGLE:
                socialLoginOauth = googleOauth;
                break;

            case KAKAO:
                socialLoginOauth = kakaoOauth;
                break;

            case NAVER:
                socialLoginOauth = naverOauth;
                break;

            default:
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
        }
    }

}
