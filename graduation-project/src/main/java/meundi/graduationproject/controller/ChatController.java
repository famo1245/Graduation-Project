package meundi.graduationproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Chat;
import meundi.graduationproject.domain.ChatRecomment;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.service.ChatService;
import meundi.graduationproject.service.MemberService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final MemberService memberService;

    @GetMapping("/chatDetail/{chat_id}")
    public String chatDetail(@PathVariable Long chat_id, Model model, HttpSession session){
        Chat chatDetail = chatService.findOne(chat_id);
        Member member;

        if(session != null && session.getAttribute("userId") != null){
            member = memberService.findById((Long)session.getAttribute("userId"));
            model.addAttribute("sessionUser", member);
        }

        model.addAttribute("chatDetail", chatDetail);
        model.addAttribute("chatRecomment", new ChatRecomment());

        return "chat/chatDetail";
    }
    @PostMapping("/chatDetail/{chat_id}")
    public String chatAddComment(@Validated @ModelAttribute ChatRecomment chatRecomment,
                                 BindingResult bindingResult, @PathVariable Long id,
                                 RedirectAttributes redirectAttributes, HttpSession session){
        redirectAttributes.addAttribute("id", id);

        if(bindingResult.hasErrors()){
            return "redirect:/chat/chatDetail/{chatId}";
        }

        chatRecomment.setChat(chatService.findOne(id));

        Member member = memberService.findById((Long)session.getAttribute("userId"));
        chatRecomment.setMember(member);
        chatService.insertChatRecomment(chatRecomment);

        return "redirect:/chat/chatDetail/{chat_id}";
    }
    @GetMapping("/chatRecomment/{chat_id}/{chatRecomment_id}/delete")
    public String chatDelete(@PathVariable Long chat_id, @PathVariable Long chatRecomment_id,
                             RedirectAttributes redirectAttributes, HttpSession session){
        ChatRecomment findComment = chatService.findChatRecomment(chatRecomment_id);
        log.info("댓글 작성자: {}", findComment.getMember().getId());
        log.info("세션 {}", (Long)session.getAttribute("userId"));

        if(Objects.equals(findComment.getMember().getId(), (Long)session.getAttribute("userId"))){
            log.info("채팅 삭제 완료");
            chatService.deleteChatRecomment(findComment);
        }
        redirectAttributes.addAttribute("chat_id", chat_id);
        return "redirect:/chat/chatDetail/{chat_id}";
    }

    @GetMapping("chatDeatil/{chat_id}/delete")
    public String chatDelete(@PathVariable Long chat_id, HttpSession session){
        Chat chat = chatService.findOne(chat_id);

        log.info("채팅 작성자: {}", chat.getMember().getId());
        log.info("세션 {}", session.getAttribute("userId"));

        if(Objects.equals(chat.getMember().getId(), (Long)session.getAttribute("userId"))){
            log.info("Comment deleted Id: {}", chat_id);
            chatService.deleteChat(chatService.findOne(chat_id));
        }

        return "redirect:/chat";
    }

}
