package meundi.graduationproject.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.repository.CultureRepository;
import meundi.graduationproject.repository.CultureRepositoryUsingJPA;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CultureService {

    @Value("${api.culture.url}")
    private String CULTURE_URL;
    @Value("${api.culture.service}")
    private String CULTURE_SERVICE;

    private final CultureRepository cultureRepository;
    private final CultureRepositoryUsingJPA CRJ;

    public Culture insertCulture(Culture culture) {
        cultureRepository.save(culture);
        return culture;
    }

    public List<Culture> findCultureAll() {
        return cultureRepository.findAll();
    }

    public List<Culture> findByCategory(String category) {
        List<Culture> cultureList = CRJ.findByCodeNameContaining(category);
        int lastIndex = cultureList.size();
        log.info("lastIndx={}", lastIndex);
        List<Culture> findList = cultureList.subList(lastIndex - 5, lastIndex);
        Collections.reverse(findList);
        return findList;
    }

    public List<Culture> findByDistrict(String district) {
        List<Culture> cultureList = CRJ.findByGunameContaining(district);
        int lastIndex = cultureList.size();
        log.info("lastIndx={}", lastIndex);
        List<Culture> findList = cultureList.subList(lastIndex - 5, lastIndex);
        Collections.reverse(findList);
        return findList;
    }

    public Boolean isEmpty() {
        return findCultureAll().isEmpty();
    }

    public Culture findOne(Long id) {
        return cultureRepository.findOneById(id);
    }

    /* Culture 객체로 받고 싶으면 .get()하면 받아짐 */
    public Optional<Culture> findOneByTitle(String title) {
        return cultureRepository.findByTitle(title);
    }


    /*최종 문화데이터 전체출력하는 메소드 */
    public String getCultureTotal() throws Exception {
        int count = Integer.parseInt(CultureTotalCount(getCultureHeader()));
        log.info("count= {}",count);
        while (true) {
            if (count < 1001) { /*1~1000이면 마지막 999개*/
                JsonToCulture(getCulture1000(String.valueOf(count)));
                break;
            }
            else {
                JsonToCulture(getCulture1000(String.valueOf(count)));
                count -= 1000;
            }
        }
        return String.valueOf(count);
    }
    /* 문화 전체 갯수 출력 */
    private String CultureTotalCount(String result) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject1 = parser.parse(result).getAsJsonObject();
        log.info("jsonObject1: {}", jsonObject1);
        JsonObject jsonObject2 = jsonObject1.getAsJsonObject(CULTURE_SERVICE);
        log.info("jsonObject2: {}", jsonObject2);
        String s = jsonObject2.get("list_total_count").getAsString();
        log.info(" CultureTotalCount: {}", s);
        /*String k = String.valueOf(Integer.parseInt(s) - 10);*/
        return s;
    }

    /* Json을 문화 엔티티에 넣어주는 메소드 */
    private String JsonToCulture(String result) throws MalformedURLException {
        /*이용할 객체들 선언*/
        JsonParser parser = new JsonParser();
        JsonObject jsonObject1 = parser.parse(result).getAsJsonObject();
        JsonObject jsonObject2 = jsonObject1.getAsJsonObject(CULTURE_SERVICE);
        JsonArray jsonArray = jsonObject2.getAsJsonArray("row");
        /* 문화 데이터 하나씩 받기 */
        for (int i=jsonArray.size()-1; i>=0;i--) {
            JsonObject childObj = (JsonObject) jsonArray.get(i);

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

    /* 1000개씩 문화 받아오기 */
    //getCulture 메서드는 묶을 수 있을듯
    private String getCulture1000(String count) throws Exception {
        String start = null;
        if (Integer.parseInt(count) < 1000) { /*마지막 단락 */
            start = "1";
        } else {
            start = String.valueOf(Integer.parseInt(count) - 999);
        }
        log.info("start = {}",start);
        log.info("count = {}",count);
        StringBuilder urlBuilder = new StringBuilder(CULTURE_URL); /*URL*/
        urlBuilder.append("/" + URLEncoder.encode("66457a68576b616e38356a61706843", "UTF-8")); /*인증키*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입*/
        urlBuilder.append("/" + URLEncoder.encode(CULTURE_SERVICE, "UTF-8")); /*서비스명*/

        // 데이터 호출은 한번에 1000개를 넘을 수 없음
        urlBuilder.append("/" + URLEncoder.encode(start, "UTF-8")); /*요청시작위치*/
        urlBuilder.append("/" + URLEncoder.encode(count, "UTF-8")); /*요청종료위치*/

        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.debug("Response code: {}", conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
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

    /* 문화 내용이 목적이 아닌, Header 목적
     * {"list_total_count":3332,"RESULT":{"CODE":"INFO-000","MESSAGE":"정상 처리되었습니다"}
     * */
    public String getCultureHeader() throws Exception {
        StringBuilder urlBuilder = new StringBuilder(CULTURE_URL); /*URL*/
        urlBuilder.append("/" + URLEncoder.encode("66457a68576b616e38356a61706843", "UTF-8")); /*인증키*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입*/
        urlBuilder.append("/" + URLEncoder.encode(CULTURE_SERVICE, "UTF-8")); /*서비스명*/

        // 데이터 호출은 한번에 1000개를 넘을 수 없음
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치*/
        urlBuilder.append("/" + URLEncoder.encode("2", "UTF-8")); /*요청종료위치*/

        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.debug("Response code: {}", conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
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

    public String getCultureEx() throws Exception {
        StringBuilder urlBuilder = new StringBuilder(CULTURE_URL); /*URL*/
        urlBuilder.append("/" + URLEncoder.encode("66457a68576b616e38356a61706843", "UTF-8")); /*인증키*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입*/
        urlBuilder.append("/" + URLEncoder.encode(CULTURE_SERVICE, "UTF-8")); /*서비스명*/

        // 데이터 호출은 한번에 1000개를 넘을 수 없음
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치*/
        urlBuilder.append("/" + URLEncoder.encode("100", "UTF-8")); /*요청종료위치*/

        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.debug("Response code: {}", conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
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

    // 매일 밤 9시마다 호출되어 업데이트를 하는 함수
    // cron = "초 분 시 일 월 요일"
    @Scheduled(cron = "0 0 21 * * *", zone = "Asia/Seoul")
    public void update() throws Exception {
        log.info("update run");
        StringBuilder urlBuilder = new StringBuilder(CULTURE_URL); /*URL*/
        urlBuilder.append("/" + URLEncoder.encode("66457a68576b616e38356a61706843", "UTF-8")); /*인증키*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입*/
        urlBuilder.append("/" + URLEncoder.encode(CULTURE_SERVICE, "UTF-8")); /*서비스명*/

        // 업데이트 시, 한번에 데이터 50개 씩 받아와서 비교
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치*/
        urlBuilder.append("/" + URLEncoder.encode("50", "UTF-8")); /*요청종료위치*/

        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.debug("Response code: {}", conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        /*이용할 객체들 선언*/
        JsonParser parser = new JsonParser();
        JsonObject jsonObject1 = parser.parse(sb.toString()).getAsJsonObject();
        JsonObject jsonObject2 = jsonObject1.getAsJsonObject("culturalEventInfo");
        JsonArray jsonArray = jsonObject2.getAsJsonArray("row");

        Culture lastOne = cultureRepository.findLastOne();

        /* 문화 데이터 하나씩 받기 */
        for (int i=jsonArray.size()-1; i>=0; i--) {
            JsonObject childObj = (JsonObject) jsonArray.get(i);

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
            if(lastOne.getTitle().equals(culture.getTitle())) {
                insertCulture(culture);
            } else {
                return;
            }
        }
    }
}




