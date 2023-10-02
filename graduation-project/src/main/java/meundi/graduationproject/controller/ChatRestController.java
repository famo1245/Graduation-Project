package meundi.graduationproject.controller;


import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Chat;
import meundi.graduationproject.domain.ChatRecomment;
import meundi.graduationproject.domain.DTO.response.ChatResponseDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.service.ChatService;
import meundi.graduationproject.service.MemberService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatService chatService;
    private final MemberService memberService;

    @GetMapping("/chatDetail/{chat_id}")
    public ResponseEntity<Map<String, Object>> chatDetail(@PathVariable Long chat_id,
        HttpSession session) {
        Map<String, Object> message = new HashMap<>();
        Chat chatDetail = chatService.findOne(chat_id);
        Member member;
        ChatResponseDTO responseDTO = new ChatResponseDTO();

        if (session != null && session.getAttribute("userId") != null) {
            member = memberService.findById((Long) session.getAttribute("userId"));
            responseDTO.creatChatResponseDTObyMember(member);
        }
        responseDTO.creatChatResponseDTO(chatDetail, new ChatRecomment());
        message.put("data", responseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/chatDetail/{chat_id}")
    public ResponseEntity<Map<String, Object>> chatAddComment(
        @Validated @RequestBody ChatRecomment chatRecomment,
        BindingResult bindingResult, @PathVariable Long chat_id, HttpSession session)
        throws BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        Map<String, Object> message = new HashMap<>();
        chatRecomment.setChat(chatService.findOne(chat_id));

        Member member = memberService.findById((Long) session.getAttribute("userId"));
        chatRecomment.setMember(member);
        chatService.insertChatRecomment(chatRecomment);
        message.put("data", chatRecomment);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/chatRecomment/{chat_id}/{chatRecomment_id}/delete")
    public ResponseEntity<Map<String, Object>> chatDelete(@PathVariable Long chat_id,
        @PathVariable Long chatRecomment_id, HttpSession session) {

        ChatRecomment findComment = chatService.findChatRecomment(chatRecomment_id);

        log.info("댓글 작성자: {}", findComment.getMember().getId());
        log.info("세션 {}", (Long) session.getAttribute("userId"));

        if (Objects.equals(findComment.getMember().getId(),
            (Long) session.getAttribute("userId"))) {
            log.info("채팅 삭제 완료");
            chatService.deleteChatRecomment(findComment);
        }
        Map<String, Object> message = new HashMap<>();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("chatDeatil/{chat_id}/delete")
    public ResponseEntity<Map<String, Object>> chatDelete(@PathVariable Long chat_id,
        HttpSession session) {
        Chat chat = chatService.findOne(chat_id);

        log.info("채팅 작성자: {}", chat.getMember().getId());
        log.info("세션 {}", session.getAttribute("userId"));

        if (Objects.equals(chat.getMember().getId(), (Long) session.getAttribute("userId"))) {
            log.info("Comment deleted Id: {}", chat_id);
            chatService.deleteChat(chatService.findOne(chat_id));
        }

        Map<String, Object> message = new HashMap<>();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}

