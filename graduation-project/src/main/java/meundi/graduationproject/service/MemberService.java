package meundi.graduationproject.service;

import meundi.graduationproject.domain.Member;
import meundi.graduationproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    * 회원 가입
    */
    public void join(Member member) {
        validateDuplicated(member);

        memberRepository.save(member);
    }

    /*
    * 회원 가입시
    * 아이디, 닉네임
    * 중복 확인
     */
    private void validateDuplicated(Member member) {
        memberRepository.findById(member.getId()).ifPresent(m -> {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다");
        });

        memberRepository.findByNickName(member.getNickName()).ifPresent(m -> {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다");
        });
    }
}
