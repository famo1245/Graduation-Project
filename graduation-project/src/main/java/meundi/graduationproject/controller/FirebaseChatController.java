package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.DTO.ChatRoomDTO;
import meundi.graduationproject.domain.DTO.MessagesDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.service.CultureService;
import meundi.graduationproject.service.FirebaseService;
import meundi.graduationproject.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FirebaseChatController {

    private final FirebaseService fbService;
    private final CultureService cultureService;
    private final MemberService memberService;

    @GetMapping("/chats/create/{culture_id}")
    public String createForm(@PathVariable Long culture_id, Model model, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            log.info("로그인을 먼저 해주세요");
            return "redirect:/members/login";
        }
        model.addAttribute("culture_id", culture_id);
        model.addAttribute("create_form", new ChatRoomDTO());
        return "friend/create-chatroom";
    }

    @PostMapping("/chats/create/{culture_id}")
    public String createChatRoom(@PathVariable Long culture_id, ChatRoomDTO chatRoomDTO,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
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
        redirectAttributes.addAttribute("room_id", roomId);
        return "redirect:/chats/{room_id}";
    }

    @GetMapping("/chats/{roomId}")
    public String showChatRoom(@PathVariable String roomId, HttpSession session,
                               Model model) throws Exception {
        if (session.getAttribute("userId") == null) {
            log.info("login first");
            return "redirect:/members/login";
        }

        Long userId = (Long) session.getAttribute("userId");
        ChatRoomDTO chatRoom = fbService.findChatRoom(roomId);
        Member currentUser = memberService.findById(userId);
        log.info("find chat room {}", roomId);

        if (fbService.joinRoom(chatRoom, currentUser)) {
            log.info("user: {} can't enter the room {}", userId, chatRoom.getRoomId());
            return "redirect:/";
        }

        List<MessagesDTO> messages = fbService.getMessages(chatRoom.getRoomId());
        model.addAttribute("chatRoom", chatRoom);
        model.addAttribute("messages", messages);
        log.info("user: {} enter the room {}", userId, chatRoom.getRoomId());
        return "friend/chatroom";
    }

    // send Message
    @PostMapping("/chats/new-message/{roomId}")
    public String sendMessage(MessagesDTO message, @PathVariable String roomId,
                              HttpSession session) throws Exception {
        Long userId = (Long) session.getAttribute("userId");
        Member find = memberService.findById(userId);
        message.setRoomId(roomId);
        message.setNickName(find.getNickName());
        message.setAuthor(userId);
        message.setCreated_at(new Date());
        fbService.sendMessage(message);
        return "redirect:/chats/{roomId}";
    }

    // notify
//    @GetMapping("/chats/notify")
//    public String sendNotify() throws Exception {
//        fbService.sendNotify();
//        return "redirect:/";
//    }
}
