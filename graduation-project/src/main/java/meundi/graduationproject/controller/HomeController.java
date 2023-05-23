package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CultureService cultureService;
    private final MemberService memberService;

    @ModelAttribute("cultureList")
    public List<Culture> getRecentCultures() {
        List<Culture> cultureListAll = cultureService.findCultureAll();
        int lastIndex = cultureListAll.size();
        List<Culture> cultureList = cultureListAll.subList(lastIndex - 5, lastIndex);
        Collections.reverse(cultureList);
        return cultureList;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        /*if (session.getAttribute("status") != null) {
            Map<String, List<Culture>> favorites = new LinkedHashMap<>();
            MemberForm myInfo = memberService.research((Long) session.getAttribute("userId"));
            for (String category : myInfo.getFavoriteCategoryList()) {
                List<Culture> cultures = cultureService.findByCategory(category);
                favorites.put(category, cultures);
            }
            model.addAttribute("favorites", favorites);
            model.addAttribute("categories", myInfo.getFavoriteCategoryList());
            model.addAttribute("districtName", myInfo.getDistrict());
        }*/
        return "index";
    }
}
