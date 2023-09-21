package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.DTO.MemberForm;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RestController
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

    @GetMapping("/api/home")
    public Map<String, Object> home(@RequestParam(name = "userId") Long userId) {
        log.info("userId={}", userId);
        Map<String, Object> data = new HashMap<>();
        if (userId != -1L) {
            log.info("true");
            MemberForm myInfo = memberService.research(userId);
            List<Culture> guList = cultureService.findByDistrict(myInfo.getDistrict());
            data.put("district", myInfo.getDistrict() + " 최신 문화 생활");
            data.put("guList", guList);
            List<String> favoriteCategoryList = myInfo.getFavoriteCategoryList();

            // 사용자가 선호 카테고리를 선택하지 않은 경우
            if (favoriteCategoryList != null) {
                int randomIndex = (int) (Math.random() * favoriteCategoryList.size());
                List<Culture> recommendList = cultureService.findByCategory(favoriteCategoryList.get(randomIndex));
//                model.addAttribute("recommendList", recommendList);
//                model.addAttribute("recommendName", favoriteCategoryList.get(randomIndex));
            }
        }

        List<Culture> recentCultures = getRecentCultures();
        data.put("recentCultures", recentCultures);
        return data;
    }
}
