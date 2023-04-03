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

    /*우선 api 읽어와서 화면에 찍음*/
    @GetMapping("/culture/get")
    @ResponseBody
    public String getCulture() throws Exception {
        return cultureService.getCulture();
    }
}
