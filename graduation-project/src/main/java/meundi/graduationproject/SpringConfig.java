package meundi.graduationproject;

import meundi.graduationproject.repository.MemberRepository;
import meundi.graduationproject.repository.MemoryMemberRepository;
import meundi.graduationproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
