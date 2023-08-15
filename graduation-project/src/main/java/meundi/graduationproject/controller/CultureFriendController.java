package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.service.FirebaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class CultureFriendController {

    private final FirebaseService firebaseService;

    @GetMapping("/create")
    public void createChatRoomForm() {

    }

    @PostMapping("/create")
    public String createChatRoom() {
        String roomId = UUID.randomUUID().toString();
        return "redirect:/api/friends/{roomId}";
    }

    @PostMapping("/{roomId}/write")
    public String sendMessage(@PathVariable String roomId, HttpSession session, String text) {
        Long userId = (Long) session.getAttribute("userId");
        firebaseService.sendMessage(userId, roomId, text);
        return "redirect:/api/friends/{roomId}";
    }
}
