package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final String ROOM_URL = "http://localhost:8080/chats/";

    public void sendNotify(String roomId, List<String> receivers) {
        String chatRoomUrl = ROOM_URL + roomId;
        log.info("sent url : {}", chatRoomUrl);
        Context context = new Context();
        context.setVariable("chatRoom_url", chatRoomUrl);
        String[] receiveList = receivers.toArray(new String[0]);

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            String content = templateEngine.process("mail/chat-notify", context);

            helper.setTo(receiveList);
            helper.setSubject("[문화 인 서울] 새로운 채팅이 있습니다.");
            helper.setText(content, true);
        };

        javaMailSender.send(preparator);
        log.info("nofity mail send completed at chat room {}", roomId);
    }
}