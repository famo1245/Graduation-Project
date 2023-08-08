package meundi.graduationproject.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.friend.DTO.FriendInsertDTO;
import meundi.graduationproject.domain.friend.DTO.FriendSearchDTO;
import meundi.graduationproject.domain.friend.Friend;
import meundi.graduationproject.service.FriendService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;

    @GetMapping()
    public String FriendHome(@ModelAttribute("friendRequest") FriendSearchDTO friendSearchDTO,
        Model model) {
        // 문화 친구 TEST용 객체
        FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
            "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
        friendService.InsertFriend(dto);
        List<Friend> friends = friendService.SearchFriend(friendSearchDTO);
        model.addAttribute("friends", friends);
        return "friend/home";
    }

}
