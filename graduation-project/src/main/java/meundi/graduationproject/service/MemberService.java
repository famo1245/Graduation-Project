package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

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
    @Transactional(readOnly = true)
    public void validateDuplicated(Member member) {
        if (memberRepository.findById(member.getId()) != null) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다");
        }

        if (!memberRepository.findByNickName(member.getNickName()).isEmpty()) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다");
        }
    }

    public Member findById(Long id) {
        return memberRepository.findById(id);
    }
}
