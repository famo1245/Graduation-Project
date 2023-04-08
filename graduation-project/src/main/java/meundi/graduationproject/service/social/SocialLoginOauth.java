package meundi.graduationproject.service.social;

public interface SocialLoginOauth {
    String getOauthRedirectURL();
    String requestAccessToken(String code);
}
