package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.service.CultureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CultureController {

    private final CultureService cultureService;

    /*우선 api 읽어와서 화면에 찍음*/
    @GetMapping("/culture/get")
    @ResponseBody
    public String getCulture() throws Exception {
        return cultureService.getCultureTotal();
    }

    @GetMapping("/culture/update")
    @ResponseBody
    public String updateCulture() throws Exception {
        return "ok";
    }

    /* 문화 10개씩 화면에 출력 */
    @GetMapping("/cultures/{page}")
    public String cultureList(@PathVariable int page, Model model) {
        List<Culture> cultureListAll = cultureService.findCultureAll();
        int lastIndex = cultureListAll.size() - 1;
        List<Culture> cultureList = cultureListAll.subList(lastIndex - page * 10, lastIndex - (page - 1) * 10);
        model.addAttribute("cultureList", cultureList);
        return "culture/cultureList";
    }

    @GetMapping("/cultures/{page}/{culture_id}")
    public String cultureDetail(@PathVariable int page, @PathVariable Long culture_id, Model model) {
        Culture findCulture = cultureService.findOne(culture_id);
        model.addAttribute("culture", findCulture);
        return "culture/cultureDetail";
    }
}
