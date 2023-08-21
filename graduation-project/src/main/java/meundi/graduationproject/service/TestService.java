package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final JavaMailSender javaMailSender;

    public void sendMail() {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(mail, "UTF-8");
        try {
            String[] receiveList = {"kandallee007@naver.com"};

            mailHelper.setTo(receiveList);
            mailHelper.setSubject("새로운 채팅이 있습니다.");
            mailHelper.setText("<p>새로운 채팅이 있습니다.</p>" +
                    "<a href=\"https://www.naver.com\" target=\"_blank\">바로 가기</a>", true);
            javaMailSender.send(mail);
            log.info("mail send completed to {}", Arrays.toString(receiveList));
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
