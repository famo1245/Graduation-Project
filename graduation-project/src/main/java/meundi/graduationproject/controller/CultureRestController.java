package meundi.graduationproject.controller;

import java.util.*;
import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.DTO.CultureDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.MemberService;
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
    private final MemberService memberService;

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
    @ResponseBody
    public CultureDTO apiCultureDetail(@PathVariable Long culture_id) {
        CultureDTO find = new CultureDTO();
        find.setCultureDTO(cultureService.findOne(culture_id));
        return find;
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
        List<Culture> temp = cultureService.findByCodename(category);
        List<CultureDTO> result = new ArrayList<>();
        for (Culture c : temp) {
            CultureDTO dto = new CultureDTO();
            dto.setCultureDTO(c);
            result.add(dto);
        }
        message.put("category", result);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/cultures/favorite")
    public ResponseEntity<Map<String, Object>> cultureListByFavorite(@RequestParam Long user_id) {
        List<CultureDTO> result = new ArrayList<>();
        Map<String, Object> message = new HashMap<>();
        if (!user_id.equals(-1L)) {
            Member byId = memberService.findById(user_id);
            result = cultureService.getFavoriteCultures(byId.getDistrict());
        }
        message.put("favorite", result);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/cultures/user/{userId}")
    public ResponseEntity<?> cultureUserLiked(@PathVariable Long userId) {
        Member user = memberService.findById(userId);
        List<Long> likedCulture = user.getLikedCulture();
        List<CultureDTO> result = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        for (Long cid : likedCulture) {
            Culture temp = cultureService.findOne(cid);
            CultureDTO dto = new CultureDTO();
            dto.setCultureDTO(temp);
            result.add(dto);
        }

        data.put("likedCultures", result);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/cultures/like/{culture_id}")
    public ResponseEntity<?> getCultureLike(@RequestParam Long userId, @PathVariable Long culture_id) {
        Member user = memberService.findById(userId);
        List<Long> likedCulture = user.getLikedCulture();

        Map<String, Object> data = new HashMap<>();
        data.put("liked", likedCulture.contains(culture_id));
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/cultures/add/like/{culture_id}")
    public ResponseEntity<?> cultureLike(@RequestParam Long userId, @PathVariable Long culture_id) {
        Member user = memberService.findById(userId);
        List<Long> likedCulture = user.getLikedCulture();
        if (likedCulture.contains(culture_id)) {
            user.removeLikedCulture(culture_id);
        } else {
            user.addLikedCulture(culture_id);
        }

        memberService.join(user);
        Map<String, Object> data = new HashMap<>();
        data.put("status", "ok");
        return ResponseEntity.ok().body(data);
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
