package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.DTO.ChatRoomDTO;
import meundi.graduationproject.domain.DTO.CultureDTO;
import meundi.graduationproject.domain.DTO.MemberForm;
import meundi.graduationproject.domain.DTO.ReviewDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.FirebaseService;
import meundi.graduationproject.service.MemberService;
import meundi.graduationproject.service.ReviewService;
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
    private final ReviewService reviewService;
    private final FirebaseService firebaseService;

    private List<CultureDTO> getRecentCultures() {
        List<Culture> cultureListAll = cultureService.findCultureAll();
        int lastIndex = cultureListAll.size();
        List<Culture> cultureList = cultureListAll.subList(lastIndex - 10, lastIndex);
        List<CultureDTO> result = new ArrayList<>();
        Collections.reverse(cultureList);
        for (Culture c : cultureList) {
            CultureDTO temp = new CultureDTO();
            temp.setCultureDTO(c);
            result.add(temp);
        }
        return result;
    }

    private List<CultureDTO> cultureConverter(List<Culture> list) {
        List<CultureDTO> result = new ArrayList<>();
        for (Culture c : list) {
            CultureDTO dto = new CultureDTO();
            dto.setCultureDTO(c);
            result.add(dto);
        }

        return result;
    }

    private List<ReviewDTO> getRecentReview() {
        List<Review> temp = reviewService.findReviewAll();
        List<ReviewDTO> temp2 = new ArrayList<>();
        int lastIndex = temp.size();
        for (Review r : temp) {
            ReviewDTO rdto = new ReviewDTO();
            rdto.setReviewDTO(r, r.getMember().getNickName(), r.getCulture().getMain_img());
            temp2.add(rdto);
        }
        if (lastIndex < 4) {
            Collections.reverse(temp2);
            return temp2;
        }
        List<ReviewDTO> recentReviews = temp2.subList(lastIndex - 4, lastIndex);
        Collections.reverse(recentReviews);
        return recentReviews;
    }

    private List<CultureDTO> getSoonEnd() {
        List<Culture> temp = cultureService.findSoonEndAll();
        List<CultureDTO> result = new ArrayList<>();
        for (Culture c : temp) {
            CultureDTO dto = new CultureDTO();
            dto.setCultureDTO(c);
            result.add(dto);
        }

        return result;
    }

    private List<ChatRoomDTO> getRecentChatRoom(List<ChatRoomDTO> list) {
        int size = list.size();
        List<ChatRoomDTO> result = list;
        if (size > 4) {
            result.subList(0, 5);
        }

        return result;
    }

    @GetMapping("/api/home")
    public Map<String, Object> home(@RequestParam(name = "userId") Long userId) throws Exception {
        log.info("userId={}", userId);
        Map<String, Object> data = new HashMap<>();
        if (userId != -1L) {
            log.info("true");
            MemberForm myInfo = memberService.research(userId);
            List<CultureDTO> guList = cultureConverter(cultureService.findByDistrict(myInfo.getDistrict()));
            data.put("district", myInfo.getDistrict() + " 최신 문화 생활");
            data.put("guList", guList);
            List<String> favoriteCategoryList = myInfo.getFavoriteCategoryList();

            // 사용자가 선호 카테고리를 선택하지 않은 경우
            if (favoriteCategoryList != null) {
                int randomIndex = (int) (Math.random() * favoriteCategoryList.size());
                List<CultureDTO> recommendList = cultureConverter(
                        cultureService.findByCategory(favoriteCategoryList.get(randomIndex)));
                data.put("category", favoriteCategoryList.get(randomIndex));
                data.put("recommendList", recommendList);
            }
        } else {
            List<CultureDTO> recentCultures = getRecentCultures();
            data.put("recentCultures", recentCultures);
            List<CultureDTO> soonEnd = getSoonEnd();
            data.put("soonEnd", soonEnd);
        }

        List<ReviewDTO> reviews = getRecentReview();
        List<ChatRoomDTO> friends = getRecentChatRoom(firebaseService.getChatRoomAll());
        data.put("reviews", reviews);
        data.put("friends", friends);
        return data;
    }
}
