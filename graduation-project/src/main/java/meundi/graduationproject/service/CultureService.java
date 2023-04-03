package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.repository.CultureRepository;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
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
        return cultureRepository.findOne(id);
    }

    public Culture findOneByTitle(String title) {
        return cultureRepository.findByName(title);
    }

    public String getCulture() throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" +  URLEncoder.encode("66457a68576b616e38356a61706843",
                "UTF-8") ); /*인증키*/
        urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입*/
        urlBuilder.append("/" + URLEncoder.encode("culturalEventInfo","UTF-8")); /*서비스명*/
        // 데이터 호출은 한번에 1000개를 넘을 수 없음
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치*/
        urlBuilder.append("/" + URLEncoder.encode("5","UTF-8")); /*요청종료위치*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

        // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
//        urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8")); /* 서비스별 추가 요청인자들*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
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

}
