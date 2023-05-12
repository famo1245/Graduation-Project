package meundi.graduationproject.service.social;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
public class NaverOauth implements SocialLoginOauth {

    @Value("${api.naver.url}")
    private String NAVER_URL;
    @Value("${api.naver.client.id}")
    private String NAVER_CLIENT_ID;
    @Value("${api.naver.callback.url}")
    private String NAVER_CALLBACK_URL;
    @Value("${api.naver.client.secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${api.naver.token.url}")
    private String NAVER_TOKEN_URL;
    @Value("${api.naver.user.url}")
    private String NAVER_USER_URL;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile");
        params.put("response_type", "code");
        params.put("client_id", NAVER_CLIENT_ID);
        params.put("redirect_uri", NAVER_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return NAVER_URL + "?" + parameterString;
    }

    @Override
    public String requestAccessToken(String code) {
        try {
            URL url = new URL(NAVER_TOKEN_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            Map<String, Object> params = new HashMap<>();
            params.put("grant_type", "authorization_code");
            params.put("client_id", NAVER_CLIENT_ID);
            params.put("client_secret", NAVER_CLIENT_SECRET);
            params.put("code", code);
            params.put("redirect_uri", NAVER_CALLBACK_URL);


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

            while((line = br.readLine())!= null){
                sb.append(line);
            }
            if(conn.getResponseCode() == 200){
                String result = sb.toString();
                log.info("response={}", result);
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                String accessToken = element.getAsJsonObject().get("access_token").getAsString();
                String refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

                return accessToken;
            }
            return "네이버 로그인 요청 처리 실패";

        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 네이저 로그인Access Token 요청 URL 입니다 :: " + NAVER_TOKEN_URL);
        }
    }

    @Override
    public HashMap<String, Object> getUserInfo(String access_token) {
        log.debug("naver: getUserInfo");

        //요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        try {
            URL url = new URL(NAVER_USER_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //요청에 필요한 Header 에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            log.info("responseCode={}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("response body={}", result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
            //변환
            Long id = convertId(response.getAsJsonObject().get("id").getAsString());
            //변환
            String gender = convertGender(response.getAsJsonObject().get("gender").getAsString());
            String email = response.getAsJsonObject().get("email").getAsString();
            String ageRange = response.getAsJsonObject().get("age").getAsString();

            userInfo.put("id", id);
            userInfo.put("gender", gender);
            userInfo.put("email", email);
            userInfo.put("age_range", ageRange);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    @Override
    public void logout(String access_Token) {
        log.info("naver logout");
    }

    private String convertGender(String gender) {
        String convertedGender = "";
        if (gender.equals("M")) {
            convertedGender = "male";
        } else if (gender.equals("F")) {
            convertedGender = "female";
        }

        return convertedGender;
    }
}
