package meundi.graduationproject.service.social;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        try {
            URL url = new URL(KAKAO_SNS_TOKEN_BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            params.put("client_id", KAKAO_SNS_CLIENT_ID);
            params.put("redirect_uri", KAKAO_SNS_CALLBACK_URL);
            params.put("grant_type", "authorization_code");

            String parameterString = params.entrySet().stream()
                    .map(x -> x.getKey() + "=" + x.getValue())
                    .collect(Collectors.joining("&"));

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedOutputStream bous = new BufferedOutputStream(conn.getOutputStream());
            bous.write(parameterString.getBytes());
            bous.flush();
            bous.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            if (conn.getResponseCode() == 200) {
                String result = sb.toString();
                log.info("response={}", result);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                String accessToken = element.getAsJsonObject().get("access_token").getAsString();
                String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

                log.info("access token={}", accessToken);
                log.info("refresh token={}", refreshToken);

                return accessToken;
            }
            return "카카오 로그인 요청 처리 실패";
        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 카카오 로그인Access Token 요청 URL 입니다 :: "
                    + KAKAO_SNS_TOKEN_BASE_URL);
        }
    }

    @Override
    public HashMap<String, Object> getUserInfo(String access_token) {
        log.debug("method:getUserInfo");

        //요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        try {
            URL url = new URL(KAKAO_SNS_USER_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //요청에 필요한 Header 에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            log.info("responseCode={}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            String result = sb.toString();

            log.info("response body={}", result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            Long id = Long.valueOf(element.getAsJsonObject().get("id").getAsString());
            userInfo.put("id", id);

            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String gender = kakao_account.getAsJsonObject().get("gender").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            String ageRange = kakao_account.getAsJsonObject().get("age_range").getAsString();

            userInfo.put("gender", gender);
            userInfo.put("email", email);
            userInfo.put("age_range", ageRange);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }

    @Override
    public void logout(String access_Token) {
        log.info("method: kakaoLogout");
        try {
            URL url = new URL(KAKAO_SNS_LOGOUT_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            log.info("responseCode={}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            String result = sb.toString();

            log.info("result={}", result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
