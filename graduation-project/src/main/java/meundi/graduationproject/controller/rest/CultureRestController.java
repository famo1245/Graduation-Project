package meundi.graduationproject.controller.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.service.CultureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CultureRestController {

    private final CultureService cultureService;

    /*우선 api 읽어와서 화면에 찍음*/
    @PostConstruct
    public void getCulture() throws Exception {
        if (cultureService.isEmpty()) {
            cultureService.getCultureTotal();
            return;
        }

        log.info("Already has Cultures");
    }

    /* 문화 10개씩 화면에 출력 */
    @GetMapping("/cultures/{page}")
    public ResponseEntity<Map<String, Object>> cultureList(@PathVariable int page) {
        List<Culture> cultureListAll = cultureService.findCultureAll();
        int lastIndex = cultureListAll.size();
        List<Culture> cultureList = cultureListAll.subList(lastIndex - (page * 10) + 1,
            lastIndex - ((page - 1) * 10));
        Collections.reverse(cultureList);
        Map<String, Object> message = new HashMap<>();

        message.put("data", cultureList);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/cultures/detail/{culture_id}")
    public ResponseEntity<Map<String, Object>> cultureDetail(@PathVariable Long culture_id) {
        Culture findCulture = cultureService.findOne(culture_id);
        Map<String, Object> message = new HashMap<>();
        message.put("data", findCulture);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/cultures/codename/{code_name}")
    public ResponseEntity<Map<String, Object>> cultureListByCodeName(
        @PathVariable String code_name) {

        Map<String, Object> message = new HashMap<>();
        message.put("data", cultureService.findByCodename(code_name));
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
