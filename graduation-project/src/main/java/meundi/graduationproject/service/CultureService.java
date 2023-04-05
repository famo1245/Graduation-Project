package meundi.graduationproject.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.repository.CultureRepository;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CultureService {

    private final CultureRepository cultureRepository;

    public Culture insertCulture(Culture culture) {
        cultureRepository.save(culture);
        return culture;
    }
    public List<Culture> findCultureAll() {
        return cultureRepository.findAll();
    }

    public Culture findOne(Long id) {
        return cultureRepository.findOneById(id);
    }

    public Culture findOneByTitle(String title) {
        return cultureRepository.findByTitle(title);
    }

    public String getCulture() throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("66457a68576b616e38356a61706843",
                "UTF-8") ); /*인증키*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입*/
        urlBuilder.append("/" + URLEncoder.encode("culturalEventInfo","UTF-8")); /*서비스명*/
        // 데이터 호출은 한번에 1000개를 넘을 수 없음
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치*/
        urlBuilder.append("/" + URLEncoder.encode("10","UTF-8")); /*요청종료위치*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.debug("Response code: {}", conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        return sb.toString();
    }

    public String JsonToCulture(String result) {
        /*이용할 객체들 선언*/
        JsonParser parser = new JsonParser();
        JsonObject jsonObject1 = parser.parse(result).getAsJsonObject();
        log.info("jsonObject1: {}", jsonObject1);
        JsonObject jsonObject2 = jsonObject1.getAsJsonObject("culturalEventInfo");
        log.info("jsonObject2: {}", jsonObject2);
        JsonArray jsonArray = jsonObject2.getAsJsonArray("row");
        log.info("jsonArray: {}", jsonArray);

        /* 문화 데이터 하나씩 받기 */
            for (Object obj : jsonArray) {
                JsonObject childObj = (JsonObject) obj;

                String title = null;
                String player = null;
                String orgLink = null;
                String mainImg = null;
                String guname = null;
                String date = null;
                String rgstdate = null;
                String codename = null;
                String userTrgt = null;
                String place = null;
                /* 문화 field 채우기 */
                try {
                    if (childObj.has("TITLE")) {
                        title = childObj.get("TITLE").getAsString();
                    }
                   ;
                    if (childObj.has("PLAYER")) {
                        player = childObj.get("PLAYER").getAsString();
                    }

                    if (childObj.has("ORG_LINK")) {
                        orgLink = childObj.get("ORG_LINK").getAsString();
                    }

                    if (childObj.has("MAIN_IMG")) {
                        mainImg = childObj.get("MAIN_IMG").getAsString();
                    }

                    if (childObj.has("GUNAME")) {
                        guname = childObj.get("GUNAME").getAsString();
                    }

                    if (childObj.has("DATE")) {
                        date = childObj.get("DATE").getAsString();
                    }

                    if (childObj.has("RGSTDATE")) {
                        rgstdate = childObj.get("RGSTDATE").getAsString();
                    }

                    if (childObj.has("CODENAME")) {
                        codename = childObj.get("CODENAME").getAsString();
                    }

                    if (childObj.has("USER_TRGT")) {
                        userTrgt = childObj.get("USER_TRGT").getAsString();
                    }

                    if (childObj.has("PLACE")) {
                        place = childObj.get("PLACE").getAsString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /* 문화 객체 생성하여, 넣기 */
                Culture culture = new Culture();
                culture.InsertCultureFromJson(title, player, orgLink, mainImg,
                        guname, date, rgstdate,
                        codename, userTrgt, place);
                insertCulture(culture);
            }
        return "OK";
        }
}

