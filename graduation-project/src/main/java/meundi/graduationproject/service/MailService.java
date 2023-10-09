package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${api.chat.base.url}")
    private String ROOM_URL;

    public void sendNotify(String roomId, List<String> receivers) {
        String chatRoomUrl = ROOM_URL + roomId;
        log.info("sent url : {}", chatRoomUrl);
        Context context = new Context();
        context.setVariable("chatRoom_url", chatRoomUrl);
        String[] receiveList = receivers.toArray(new String[0]);

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            String content = templateEngine.process("templates/mail/chat-notify.html", context);

            helper.setTo(receiveList);
            helper.setSubject("[문화 인 서울] 새로운 채팅이 있습니다.");
            helper.setText(content, true);
        };

        javaMailSender.send(preparator);
        log.info("nofity mail send completed at chat room {}", roomId);
    }
}