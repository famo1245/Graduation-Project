package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.DTO.ChatRoomDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.FirebaseService;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FirebaseChatController {

    private final FirebaseService fbService;
    private final CultureService cultureService;
    private final MemberService memberService;

    @GetMapping("/chats/create/{culture_id}")
    public String createForm(@PathVariable Long culture_id, Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            log.info("로그인을 먼저 해주세요");
            return "redirect:/cultures/detail/{culture_id}";
        }
        model.addAttribute("culture_id", culture_id);
        model.addAttribute("create_form", new ChatRoomDTO());
        return "friend/create-chatroom";
    }

    @PostMapping("/chats/create/{culture_id}")
    public String createChatRoom(@PathVariable Long culture_id, ChatRoomDTO chatRoomDTO, HttpSession session) {
        Culture find = cultureService.findOne(culture_id);
        Long userId = (Long) session.getAttribute("userId");
        String roomId = UUID.randomUUID().toString();

        chatRoomDTO.setCultureId(culture_id);
        chatRoomDTO.setCultureTitle(find.getTitle());
        chatRoomDTO.setCultureImg(find.getMain_img());
        chatRoomDTO.setAuthorId(userId);
        chatRoomDTO.setRoomId(roomId);
        chatRoomDTO.addParticipants(userId);
        fbService.createChatRoom(chatRoomDTO);
        log.info("user: {} created {} chat room", userId, roomId);
//        return "redirect:/cultures/detail/{culture_id}";
        return "forward:/chats/{roomId}";
    }

    @GetMapping("/chats/{roomId}")
    @ResponseBody
    public String showChatRoom(@PathVariable String roomId, HttpSession session) throws Exception {
        if (session.getAttribute("userId") == null) {
            log.info("로그인을 먼저 해주세요");
            return "redirect:/cultures/detail/{culture_id}";
        }

        Long userId = (Long) session.getAttribute("userId");
        ChatRoomDTO chatRoom = fbService.findChatRoom(roomId);
        Member currentUser = memberService.findById(userId);
        log.info("find chat room {}", roomId);

        if (fbService.joinRoom(chatRoom, currentUser)) {
            log.info("user: {} can't enter the room {}", userId, chatRoom.getRoomId());
            return "redirect:/";
        }

        log.info("user: {} enter the room {}", userId, chatRoom.getRoomId());
        return "OK";
    }

    // send Message
    // notify
}
