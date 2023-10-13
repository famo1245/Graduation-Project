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

    @PostMapping("/chats/create/{culture_id}")
    @ResponseBody
    public ResponseEntity<?> createChatRoom(@PathVariable Long culture_id, @RequestBody Map<String, Object> body,
                                         @RequestParam Long userId) {
        Culture find = cultureService.findOne(culture_id);
        Member author = memberService.findById(userId);
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        String roomId = UUID.randomUUID().toString();
        log.info("body={}", body);

        chatRoomDTO.setCultureId(culture_id);
        chatRoomDTO.setCultureTitle(find.getTitle());
        chatRoomDTO.setCultureImg(find.getMain_img());
        chatRoomDTO.setAuthorId(userId);
        chatRoomDTO.setAuthorNickname(author.getNickName());
        chatRoomDTO.setRoomId(roomId);
        chatRoomDTO.addParticipants(userId);
        chatRoomDTO.setAvailableAgeRange((String) body.get("availableAgeRange"));
        chatRoomDTO.setMax(Integer.parseInt((String) body.get("max")));
        chatRoomDTO.setMeetDate((String) body.get("meetDate"));
        chatRoomDTO.setGender((String) body.get("gender"));
        chatRoomDTO.setTitle((String) body.get("friendContents"));
        fbService.createChatRoom(chatRoomDTO);
        log.info("user: {} created {} chat room", userId, roomId);
        Map<String, Object> data = new HashMap<>();
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/chats/{roomId}")
    public ResponseEntity<?> showChatRoom(@PathVariable String roomId, @RequestParam Long userId) throws Exception {
        ChatRoomDTO chatRoom = fbService.findChatRoom(roomId);
        Member currentUser = memberService.findById(userId);
        log.info("find chat room {}", roomId);

        Map<String, Object> result = fbService.joinRoom(chatRoom, currentUser);
        if ((boolean) result.get("accessible")) {
            List<MessagesDTO> messages = fbService.getMessages(chatRoom.getRoomId());
            result.put("chats", messages);
        }

        return ResponseEntity.ok().body(result);
    }

    // send Message
    @PostMapping("/chats/new-message/{roomId}")
    public ResponseEntity<?> sendMessage(@RequestBody MessagesDTO message, @PathVariable String roomId, @RequestParam Long userId)
            throws Exception {
        log.info("message={}", message);
        Member find = memberService.findById(userId);
        message.setRoomId(roomId);
        message.setNickName(find.getNickName());
        message.setAuthor(userId);
        message.setCreated_at(new Date());
        fbService.sendMessage(message);
        Map<String, Object> data = new HashMap<>();
        data.put("chat", message);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/chatRooms")
    public ResponseEntity<?> getChatRoomAll() throws Exception {
        Map<String, Object> data = new HashMap<>();
        List<ChatRoomDTO> chatRooms = fbService.getChatRoomAll();
        data.put("chatRooms", chatRooms);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/chatRooms/user/{userId}")
    public ResponseEntity<?> getUserChatRoom(@PathVariable Long userId) throws Exception {
        Map<String, Object> data = new HashMap<>();
        List<ChatRoomDTO> chatRooms = fbService.getUserChatRooms(userId);
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
