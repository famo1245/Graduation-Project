package meundi.graduationproject.service.social;

import java.util.HashMap;

public interface SocialLoginOauth {
    String getOauthRedirectURL();
    String requestAccessToken(String code);
    HashMap<String, Object> getUserInfo(String access_token);
    void logout(String access_Token);

    default Long convertId(String id) {
        long multiplicand = 2L;
        long convertedId = 0L;
        char[] idList = id.toCharArray();
        for (char c : idList) {
            convertedId += c * multiplicand;
            multiplicand *= 2L;
        }

        return convertedId;
    }
}
