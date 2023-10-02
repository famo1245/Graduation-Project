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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FirebaseChatController {

    private final FirebaseService fbService;
    private final CultureService cultureService;
    private final MemberService memberService;

    @GetMapping("/chats/create/{culture_id}")
    public String createForm(@PathVariable Long culture_id, Model model) {
        model.addAttribute("culture_id", culture_id);
        model.addAttribute("create_form", new ChatRoomDTO());
        return "friend/create-chatroom";
    }

    @PostMapping("/chats/create/{culture_id}")
    @ResponseBody
    public ResponseEntity<?> createChatRoom(@PathVariable Long culture_id, ChatRoomDTO chatRoomDTO,
                                         @RequestParam Long userId) {
        Culture find = cultureService.findOne(culture_id);
        Member author = memberService.findById(userId);
        log.info("userId={}", userId);
        log.info("chatroom={}", chatRoomDTO);
        log.info("culture={}", find);

//        String roomId = UUID.randomUUID().toString();
//
//        chatRoomDTO.setCultureId(culture_id);
//        chatRoomDTO.setCultureTitle(find.getTitle());
//        chatRoomDTO.setCultureImg(find.getMain_img());
//        chatRoomDTO.setAuthorId(userId);
//        chatRoomDTO.setAuthorNickname(author.getNickName());
//        chatRoomDTO.setRoomId(roomId);
//        chatRoomDTO.addParticipants(userId);
//        fbService.createChatRoom(chatRoomDTO);
//        log.info("user: {} created {} chat room", userId, roomId);
        Map<String, Object> data = new HashMap<>();
//        data.put("chatRoom", chatRoomDTO);
        return ResponseEntity.ok().body(data);
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

    @GetMapping("/chatRooms")
    public ResponseEntity<?> getChatRoomAll() throws Exception {
        Map<String, Object> data = new HashMap<>();
        List<ChatRoomDTO> chatRooms = fbService.getChatRoomAll();
        data.put("chatRooms", chatRooms);
        return ResponseEntity.ok().body(data);
    }

    // notify
//    @GetMapping("/chats/notify")
//    public String sendNotify() throws Exception {
//        fbService.sendNotify();
//        return "redirect:/";
//    }
}
