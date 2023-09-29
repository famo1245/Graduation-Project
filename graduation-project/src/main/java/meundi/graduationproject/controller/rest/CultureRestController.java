package meundi.graduationproject.controller.rest;

import java.util.*;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.service.CultureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //    @GetMapping("/cultures/detail/{culture_id}")
    public ResponseEntity<Map<String, Object>> cultureDetail(@PathVariable Long culture_id) {
        Culture findCulture = cultureService.findOne(culture_id);
        Map<String, Object> message = new HashMap<>();
        message.put("data", findCulture);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/cultures/codename/{code_name}")
    public ResponseEntity<Map<String, Object>> cultureListByCodeName(@PathVariable String code_name, Model model) {
        Map<String, String> categories = (HashMap) model.getAttribute("categories");

        String category;
        if (categories.containsKey(code_name)) {
            category = categories.get(code_name);
        } else {
            category = code_name;
        }

        Map<String, Object> message = new HashMap<>();
        message.put("categoryName", category);
        message.put("category", cultureService.findByCodename(category));
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @ModelAttribute("categories")
    public Map<String, String> getCategories() {
        Map<String, String> categories = new HashMap<>();
        categories.put("뮤지컬", "뮤지컬/오페라");
        categories.put("문화교양", "문화교양/강좌");
        categories.put("교육", "교육/체험");
        categories.put("독주", "독주/독창회");
        categories.put("전시", "전시/미술");
        categories.put("축제-문화", "축제-문화/예술");
        categories.put("축제-전통", "축제-전통/역사");
        return categories;
    }
}
