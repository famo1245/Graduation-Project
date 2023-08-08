package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.DTO.MemberForm;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeController {

    private final CultureService cultureService;
    private final MemberService memberService;

    private List<Culture> getRecentCultures() {
        List<Culture> cultureListAll = cultureService.findCultureAll();
        int lastIndex = cultureListAll.size();
        List<Culture> cultureList = cultureListAll.subList(lastIndex - 10, lastIndex);
        Collections.reverse(cultureList);
        return cultureList;
    }

    @GetMapping("/home")
    public Map<String, List> home(HttpSession session, Model model) {
        Map<String, List> data = new HashMap<>();

        if (session.getAttribute("status") != null) {
            MemberForm myInfo = memberService.research((Long) session.getAttribute("userId"));
            List<Culture> guList = cultureService.findByDistrict(myInfo.getDistrict());
            model.addAttribute("guList", guList);
            model.addAttribute("district", myInfo.getDistrict());
            List<String> favoriteCategoryList = myInfo.getFavoriteCategoryList();

            // 사용자가 선호 카테고리를 선택하지 않은 경우
            if (favoriteCategoryList != null) {
                int randomIndex = (int) (Math.random() * favoriteCategoryList.size());
                List<Culture> recommendList = cultureService.findByCategory(favoriteCategoryList.get(randomIndex));
                model.addAttribute("recommendList", recommendList);
                model.addAttribute("recommendName", favoriteCategoryList.get(randomIndex));
            }
        }

        List<Culture> recentCultures = getRecentCultures();
        data.put("recentCultures", recentCultures);
        return data;
    }
}
