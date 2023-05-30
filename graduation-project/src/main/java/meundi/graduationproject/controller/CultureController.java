package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.service.CultureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CultureController {

    private final CultureService cultureService;

    /*우선 api 읽어와서 화면에 찍음*/
    @PostConstruct
    public void getCulture() throws Exception {
        if(cultureService.isEmpty()) {
            cultureService.getCultureTotal();
            return;
        }

        log.info("Already has Cultures");
    }

    /* 문화 10개씩 화면에 출력 */
    @GetMapping("/cultures/{page}")
    public String cultureList(@PathVariable int page, Model model) {
        List<Culture> cultureListAll = cultureService.findCultureAll();
        int lastIndex = cultureListAll.size();
        List<Culture> cultureList = cultureListAll.subList(lastIndex - (page * 10) + 1, lastIndex - ((page - 1) * 10));
        Collections.reverse(cultureList);
        model.addAttribute("cultureList", cultureList);
        return "culture/cultureList";
    }

    @GetMapping("/cultures/detail/{culture_id}")
    public String cultureDetail(@PathVariable Long culture_id, Model model) {
        Culture findCulture = cultureService.findOne(culture_id);
        model.addAttribute("culture", findCulture);
        return "culture/cultureDetail";
    }
}
