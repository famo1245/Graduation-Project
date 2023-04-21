package meundi.graduationproject.service.social;

import java.util.HashMap;

public interface SocialLoginOauth {
    String getOauthRedirectURL();
    String requestAccessToken(String code);
    HashMap<String, Object> getUserInfo(String access_token);
    void logout(String access_Token);
}
