package meundi.graduationproject.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.friend.DTO.FriendInsertDTO;
import meundi.graduationproject.domain.friend.DTO.FriendSearchDTO;
import meundi.graduationproject.domain.friend.Friend;
import meundi.graduationproject.repository.FriendRepository;
import meundi.graduationproject.service.FriendService;
import meundi.graduationproject.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/friend")
public class FriendController {

    private final FriendService friendService;
    private final FriendRepository friendRepository;
    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<?> FriendHome() {
        Map<String, Object> message = new HashMap<>();
        message.put("status", 200);
        message.put("data", friendRepository.findAll());

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    @PostMapping()
    public ResponseEntity<?> FriendHomeBySearch(@RequestBody FriendSearchDTO friendSearchDTO){
        Map<String, Object> message = new HashMap<>();
        message.put("status", 200);
        message.put("data", friendService.SearchFriend(friendSearchDTO));
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/{friend_id}")
    public ResponseEntity<?> FriendDetail(@PathVariable("friend_id") Long friend_id){
        Map<String, Object> message = new HashMap<>();
        message.put("status", 200);
        message.put("data", friendRepository.findById(friend_id).get());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/creat")
    public ResponseEntity<?> FriendCreat(@Validated @RequestBody FriendInsertDTO dto,
        BindingResult bindingResult, HttpSession session) throws BindException {
        Member member = null;
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        if (session.getAttribute("userId") != null) {
            member = memberService.findById((Long) session.getAttribute("userId"));

        }
        Map<String, Object> message = new HashMap<>();
        message.put("status", 200);
        message.put("data", friendService.InsertFriend(dto, member));
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
