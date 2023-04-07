package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.login.SocialLoginType;
import meundi.graduationproject.service.social.GoogleOauth;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final GoogleOauth googleOauth;
//    private final KakaoOauth kakaoOauth;
    private final HttpServletResponse response;

    //switch 통해서 소셜로그인 방식 선택
    public void request(SocialLoginType socialLoginType){
        String redirectURL ="";
        switch (socialLoginType) {
            case GOOGLE: {
                redirectURL = googleOauth.getOauthRedirectURL();
            } break;
            case KAKAO:{
//                redirectURL = kakaoOauth.getOauthRedirectURL();
            } break;
            default:{       //위에 적혀있지 않은 로그인 방식을 default로 처리
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
        try {   //redirectURL 전송 불가시 IOException
            response.sendRedirect(redirectURL);
        }catch (IOException e){
            e.printStackTrace();;
        }
    }
}
