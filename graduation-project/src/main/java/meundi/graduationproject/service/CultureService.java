package meundi.graduationproject.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.DTO.CultureDTO;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CultureService {

    @Value("${api.culture.url}")
    private String CULTURE_URL;
    @Value("${api.culture.service}")
    private String CULTURE_SERVICE;
    private final int dateDiff = 5;

    private final CultureRepository cultureRepository;
    private final CultureRepositoryUsingJPA CRJ;

    public Culture insertCulture(Culture culture) {
        cultureRepository.save(culture);
        return culture;
    }

    public List<Culture> findCultureAll() {
        return cultureRepository.findAll();
    }

    public List<Culture> findSoonEndAll() {
        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, dateDiff);
        Date endDate = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM");
        String start = format.format(startDate);
        String end = format.format(endDate);
        List<Culture> temp = CRJ.findAllByEndDateBetween(startDate, endDate);
        int lastIndex = temp.size();
        List<Culture> soonEnd = temp.subList(lastIndex - 10, lastIndex);
        Collections.reverse(soonEnd);
        return soonEnd;
    }
    public List<Culture> findByCodename(String codename) {
        List<Culture> findCultures = CRJ.findByCodeName(codename);
        Collections.reverse(findCultures);
        return findCultures;
    }

    public List<Culture> findByCategory(String category) {
        List<Culture> cultureList = CRJ.findByCodeNameContaining(category);
        int lastIndex = cultureList.size();
        log.info("lastIndx={}", lastIndex);
        List<Culture> findList = cultureList.subList(lastIndex - 10, lastIndex);
        Collections.reverse(findList);
        return findList;
    }

    public List<Culture> findByDistrict(String district) {
        List<Culture> cultureList = CRJ.findByGunameContaining(district);
        int lastIndex = cultureList.size();
        log.info("lastIndx={}", lastIndex);
        List<Culture> findList = cultureList.subList(lastIndex - 10, lastIndex);
        Collections.reverse(findList);
        return findList;
    }

    public Boolean isEmpty() {
        return findCultureAll().isEmpty();
    }

    public Culture findOne(Long id) {
//        return cultureRepository.findOneById(id);
        return CRJ.findDistinctByIdEquals(id);
    }

    /* Culture 객체로 받고 싶으면 .get()하면 받아짐 */
    public Optional<Culture> findOneByTitle(String title) {
        return cultureRepository.findByTitle(title);
    }


    /*최종 문화데이터 전체출력하는 메소드 */
    public String getCultureTotal() throws Exception {
        int count = Integer.parseInt(CultureTotalCount(getCultureHeader()));
        log.info("count= {}", count);
        while (true) {
            if (count < 1001) { /*1~1000이면 마지막 999개*/
                JsonToCulture(getCulture1000(String.valueOf(count)));
                break;
            } else {
                JsonToCulture(getCulture1000(String.valueOf(count)));
                count -= 1000;
            }
        }
        return String.valueOf(count);
    }

    public List<CultureDTO>  getFavoriteCultures(String guName) {
        Map<String, Integer> gu = new HashMap<>();
        gu.put("종로구",0);
        gu.put("중구",1);
        gu.put("용산구",2);
        gu.put("성동구",3);
        gu.put("광진구",4);
        gu.put("동대문구",5);
        gu.put("중랑구",6);
        gu.put("성북구",7);
        gu.put("강북구",8);
        gu.put("도봉구",9);
        gu.put("노원구",10);
        gu.put("은평구",11);
        gu.put("서대문구",12);
        gu.put("마포구",13);
        gu.put("양천구",14);
        gu.put("강서구",15);
        gu.put("구로구",16);
        gu.put("금천구",17);
        gu.put("영등포구",18);
        gu.put("동작구",19);
        gu.put("관악구",20);
        gu.put("서초구",21);
        gu.put("강남구",22);
        gu.put("송파구",23);
        gu.put("강동구",24);

        ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 25; i++){
            graph.add(new ArrayList<Integer>());
        }
        graph.get(0).add(11);
        graph.get(11).add(0);
        graph.get(0).add(12);
        graph.get(12).add(0);
        graph.get(0).add(1);
        graph.get(1).add(0);
        graph.get(0).add(7);
        graph.get(7).add(0);
        graph.get(1).add(12);
        graph.get(12).add(1);
        graph.get(1).add(13);
        graph.get(13).add(1);
        graph.get(1).add(2);
        graph.get(2).add(1);
        graph.get(1).add(3);
        graph.get(3).add(1);
        graph.get(1).add(5);
        graph.get(5).add(1);
        graph.get(2).add(13);
        graph.get(13).add(2);
        graph.get(2).add(18);
        graph.get(18).add(2);
        graph.get(2).add(19);
        graph.get(19).add(2);
        graph.get(2).add(21);
        graph.get(21).add(2);
        graph.get(2).add(22);
        graph.get(22).add(2);
        graph.get(2).add(3);
        graph.get(3).add(2);
        graph.get(3).add(5);
        graph.get(5).add(3);
        graph.get(3).add(4);
        graph.get(4).add(3);
        graph.get(3).add(22);
        graph.get(22).add(3);
        graph.get(4).add(5);
        graph.get(5).add(4);
        graph.get(4).add(6);
        graph.get(6).add(4);
        graph.get(4).add(24);
        graph.get(24).add(4);
        graph.get(4).add(23);
        graph.get(23).add(4);
        graph.get(5).add(6);
        graph.get(6).add(5);
        graph.get(5).add(7);
        graph.get(7).add(5);
        graph.get(6).add(10);
        graph.get(10).add(6);
        graph.get(6).add(7);
        graph.get(7).add(6);
        graph.get(7).add(10);
        graph.get(10).add(7);
        graph.get(7).add(8);
        graph.get(8).add(7);
        graph.get(8).add(9);
        graph.get(9).add(8);
        graph.get(8).add(10);
        graph.get(10).add(8);
        graph.get(9).add(10);
        graph.get(10).add(9);
        graph.get(11).add(12);
        graph.get(12).add(11);
        graph.get(11).add(13);
        graph.get(13).add(11);
        graph.get(12).add(13);
        graph.get(13).add(12);
        graph.get(13).add(18);
        graph.get(18).add(13);
        graph.get(13).add(15);
        graph.get(15).add(13);
        graph.get(14).add(15);
        graph.get(15).add(14);
        graph.get(14).add(18);
        graph.get(18).add(14);
        graph.get(14).add(16);
        graph.get(16).add(14);
        graph.get(16).add(18);
        graph.get(18).add(16);
        graph.get(16).add(20);
        graph.get(20).add(16);
        graph.get(16).add(17);
        graph.get(17).add(16);
        graph.get(17).add(20);
        graph.get(20).add(17);
        graph.get(18).add(19);
        graph.get(19).add(18);
        graph.get(19).add(20);
        graph.get(20).add(19);
        graph.get(19).add(21);
        graph.get(21).add(19);
        graph.get(20).add(21);
        graph.get(21).add(20);
        graph.get(21).add(22);
        graph.get(22).add(21);
        graph.get(22).add(23);
        graph.get(23).add(22);
        graph.get(23).add(24);
        graph.get(24).add(23);
        ArrayList<String> guNames = new ArrayList<>();
        /* 현재 주소 */
        guNames.add(guName);
        for (int i : graph.get(gu.get(guName))) {
            Set<String> strings = gu.keySet();
            for (String string : strings) {
                if (gu.get(string).equals(i)){
                    /* 주변구들*/
                    guNames.add(string);
                }
            }
        }
        List<CultureDTO> cultureDTOS = new ArrayList<>();
        for (String name : guNames) {
            List<Culture> cultures = CRJ.findByGunameContaining(name);
            for (Culture culture : cultures) {
                cultureDTOS.add(new CultureDTO(culture));
            }
        }
        return cultureDTOS;

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /* 문화 데이터 하나씩 받기 */
        for (int i = jsonArray.size() - 1; i >= 0; i--) {
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
            String useFee = null;
            Date endDate = null;

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

                if (childObj.has("USE_TRGT")) {
                    userTrgt = childObj.get("USE_TRGT").getAsString();
                }

                if (childObj.has("PLACE")) {
                    place = childObj.get("PLACE").getAsString();
                }

                if (childObj.has("END_DATE")) {
                    endDate = format.parse(childObj.get("END_DATE").getAsString());
                }

                if (childObj.has("IS_FREE")) {
                    useFee = childObj.get("IS_FREE").getAsString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /* 문화 객체 생성하여, 넣기 */
            Culture culture = new Culture();
            culture.InsertCultureFromJson(title, player, orgLink, mainImg, guname, date,
                    rgstdate, codename, userTrgt, place, endDate, useFee);
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
        log.info("start = {}", start);
        log.info("count = {}", count);
        StringBuilder urlBuilder = new StringBuilder(CULTURE_URL); /*URL*/
        urlBuilder.append(
            "/" + URLEncoder.encode("66457a68576b616e38356a61706843", "UTF-8")); /*인증키*/
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
            rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(
                new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
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
        urlBuilder.append(
            "/" + URLEncoder.encode("66457a68576b616e38356a61706843", "UTF-8")); /*인증키*/
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
            rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(
                new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
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
        urlBuilder.append(
            "/" + URLEncoder.encode("66457a68576b616e38356a61706843", "UTF-8")); /*인증키*/
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
            rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(
                new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
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

    // 매일 새벽 4시마다 호출되어 업데이트를 하는 함수
    // cron = "초 분 시 일 월 요일"
    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    public void update() throws Exception {
        log.info("update run");
        StringBuilder urlBuilder = new StringBuilder(CULTURE_URL); /*URL*/
        urlBuilder.append(
            "/" + URLEncoder.encode("66457a68576b616e38356a61706843", "UTF-8")); /*인증키*/
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
            rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(
                new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Culture lastOne = cultureRepository.findLastOne();

        /* 문화 데이터 하나씩 받기 */
        for (int i = jsonArray.size() - 1; i >= 0; i--) {
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
            Date endDate = null;
            String useFee = null;

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

                if (childObj.has("END_DATE")) {
                    endDate = format.parse(childObj.get("END_DATE").getAsString());
                }

                if (childObj.has("IS_FREE")) {
                    useFee = childObj.get("IS_FREE").getAsString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            /* 문화 객체 생성하여, 넣기 */
            Culture culture = new Culture();
            culture.InsertCultureFromJson(title, player, orgLink, mainImg, guname, date,
                    rgstdate, codename, userTrgt, place, endDate, useFee);
            if (lastOne.equals(culture)) {
                return;
            }

            insertCulture(culture);
        }
    }
}




