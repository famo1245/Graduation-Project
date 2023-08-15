package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Messages;
import meundi.graduationproject.service.FirebaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exam/svc/v1")
@Slf4j
public class FirebaseController {

    private final FirebaseService firebaseService;

    @GetMapping("/chats")
    public ResponseEntity<Object> getMessages() throws ExecutionException, InterruptedException {
        List<Messages> list = firebaseService.getMessages();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/add")
    public String sendMessage(@RequestParam(name = "text") String text) throws Exception {
        log.info("text={}", text);
        firebaseService.insertMessage(text);
        return "OK";
    }
}
