package meundi.graduationproject.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@Slf4j
public class TestService {

    public void sendMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        try {
            String[] receiveList = {"kandallee007@naver.com"};

            simpleMailMessage.setTo(receiveList);

            simpleMailMessage.setSubject("test_title");
            simpleMailMessage.setText("test_content");
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
