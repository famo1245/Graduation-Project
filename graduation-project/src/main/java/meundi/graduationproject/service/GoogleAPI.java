package meundi.graduationproject.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Slf4j
public class GoogleAPI {

    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;
    @Value("${sns.google.user.url}")
    private String GOOGLE_USER_URL;

    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "https://www.googleapis.com/auth/profile.agerange.read https://www.googleapis.com/auth/profile.emails.read https://www.googleapis.com/auth/user.gender.read https://www.googleapis.com/auth/userinfo.profile");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
    }
    public String requestAccessToken(String code) {
        try {
            URL url = new URL(GOOGLE_SNS_TOKEN_BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            Map<String, Object> params = new HashMap<>();
            params.put("code", code);
            params.put("client_id", GOOGLE_SNS_CLIENT_ID);
            params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
            params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
            params.put("grant_type", "authorization_code");

            String parameterString = params.entrySet().stream()
                    .map(x -> x.getKey() + "=" + x.getValue())
                    .collect(Collectors.joining("&"));

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

                return accessToken;
            }
            return "구글 로그인 요청 처리 실패";
        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 구글 로그인 Access Token 요청 URL 입니다 :: " + GOOGLE_SNS_TOKEN_BASE_URL);
        }
    }

    // 수정 예정
    public String getUserInfo(String access_token) {
        log.info("google: getUserInfo");

        HashMap<String, Object> userInfo = new HashMap<>();
        try {
            URL url = new URL("https://people.googleapis.com/v1/people/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //요청에 필요한 Header 에 포함될 내용
//            conn.setRequestProperty("Content-Length", "256");
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            log.info("request: {}", conn.toString());
            int responseCode = conn.getResponseCode();
            log.info("responseCode={}", responseCode);

            BufferedReader br;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            return result;
            //google people api 사용으로 다시 할 것
            /*log.info("response body={}", result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            Long id = Long.valueOf(element.getAsJsonObject().get("id").getAsString());
            userInfo.put("id", id);

            String gender = element.getAsJsonObject().get("gender").getAsString();
            String email = element.getAsJsonObject().get("email").getAsString();
            String ageRange = element.getAsJsonObject().get("age_range").getAsString();

            userInfo.put("gender", gender);
            userInfo.put("email", email);
            userInfo.put("age_range", ageRange);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }
}