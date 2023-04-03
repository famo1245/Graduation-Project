package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.service.CultureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CultureController {

    private final CultureService cultureService;

    /*화면에 OK 찍히면, H2 dataBase에서 Culture 엔티티 확인 */
    @GetMapping("/culture/get")
    @ResponseBody
    public String getCulture() throws Exception {
        return cultureService.JsonToCulture(cultureService.getCulture());
    }
}
