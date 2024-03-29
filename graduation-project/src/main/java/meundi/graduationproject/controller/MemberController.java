package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.DTO.MemberForm;
import meundi.graduationproject.domain.DTO.ReviewDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "members/loginForm";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    @ResponseBody
    public String create(@RequestBody MemberForm form) {
        log.info("member={}", form);
        Member member = memberService.createMember(form);
        memberService.join(member);

        return "ok";
    }

    @GetMapping("/check-nickname")
    @ResponseBody
    public ResponseEntity<?> checkNickName(@RequestParam String nickName) {
        Boolean isDuplicated = memberService.validateDuplicatedNickName(nickName);
        Map<String, Object> data = new HashMap<>();
        data.put("isDuplicated", isDuplicated);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/info")
    @ResponseBody
    public ResponseEntity<?> myInfo(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        Member m = memberService.findById(userId);
        List<Review> temp = m.getReviews();
        List<ReviewDTO> myReviews = convertReview(temp);
        MemberForm myInfo = memberService.research(userId);
        Map<String, Object> data = new HashMap<>();
        if (myInfo != null) {
            data.put("myInfo", myInfo);
            data.put("myReviews", myReviews);
        }

        return ResponseEntity.ok().body(data);
    }

    private List<ReviewDTO> convertReview(List<Review> temp) {
        int size = temp.size();
        List<ReviewDTO> result = new ArrayList<>();
        if (size > 10) {
            temp.subList(size - 10, size);
        }

        Collections.reverse(temp);
        for (Review r : temp) {
            ReviewDTO dto = new ReviewDTO();
            dto.setReviewDTO(r, r.getMember().getNickName(), r.getCulture().getMain_img());
            result.add(dto);
        }

        return result;
    }

    @GetMapping("/info/update")
    public String updateInfo(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        MemberForm myInfo = memberService.research(userId);
        if (myInfo != null) {
            model.addAttribute("memberForm", myInfo);
        }
        return "members/update-myInfo";
    }

    //memberForm 으로 변환 해야함
    @PostMapping("/info/update")
    @ResponseBody
    public String afterUpdate(@RequestBody Map<String, Object> data) {
        MemberForm myInfo = new MemberForm();
        myInfo.setNickName((String) data.get("nickName"));
        myInfo.setDistrict((String) data.get("district"));
        myInfo.setFavoriteCategory((String) data.get("favoriteCategory"));
        Long userId = (Long) data.get("userId");

        memberService.updateMember(userId, myInfo);
        return "ok";
    }

    //지역구
    @ModelAttribute("districts")
    public List<String> districtName() {
        List<String> districts = new ArrayList<>();
        districts.add("강남구");
        districts.add("강동구");
        districts.add("강북구");
        districts.add("강서구");
        districts.add("관악구");
        districts.add("광진구");
        districts.add("구로구");
        districts.add("금천구");
        districts.add("노원구");
        districts.add("도봉구");
        districts.add("동대문구");
        districts.add("동작구");
        districts.add("마포구");
        districts.add("서대문구");
        districts.add("서초구");
        districts.add("성동구");
        districts.add("성북구");
        districts.add("송파구");
        districts.add("양천구");
        districts.add("영등포구");
        districts.add("용산구");
        districts.add("은평구");
        districts.add("종로구");
        districts.add("중구");
        districts.add("중랑구");
        return districts;
    }

    //카테고리
    @ModelAttribute("categories")
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("뮤지컬/오페라");
        categories.add("클래식");
        categories.add("축제");
        categories.add("문화교양/강좌");
        categories.add("연극");
        return categories;
    }
}
