package meundi.graduationproject.service.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class KakaoOauth implements SocialLoginOauth {

    @Value("${api.kakao.url}")
    private String KAKAO_SNS_BASE_URL;
    @Value("${api.kakao.client.id}")
    private String KAKAO_SNS_CLIENT_ID;
    @Value("${api.kakao.callback.url}")
    private String KAKAO_SNS_CALLBACK_URL;
    @Value("${api.kakao.token.url}")
    private String KAKAO_SNS_TOKEN_BASE_URL;
    @Value("${api.kakao.user.url}")
    private String KAKAO_SNS_USER_URL;
    @Value("${api.kakao.logout.url}")
    private String KAKAO_SNS_LOGOUT_URL;

    @Override
    public String getOauthRedirectURL() {
        log.info("kakao get redirect url");
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", KAKAO_SNS_CLIENT_ID);
        params.put("redirect_uri", KAKAO_SNS_CALLBACK_URL);
        params.put("response_type", "code");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return KAKAO_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public String requestAccessToken(String code) {
        return null;
    }

    @Override
    public HashMap<String, Object> getUserInfo(String access_token) {
        return null;
    }

    @Override
    public void logout(String access_Token) {

    }
}
