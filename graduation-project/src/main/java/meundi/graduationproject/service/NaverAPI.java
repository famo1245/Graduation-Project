package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

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
public class NaverAPI {

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
                log.info("response={}", sb.toString());
                return sb.toString();
            }
            return "네이버 로그인 요청 처리 실패";

        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 네이저 로그인Access Token 요청 URL 입니다 :: " + NAVER_TOKEN_URL);
        }
    }
}
