package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meundi.graduationproject.controller.MemberForm;
import meundi.graduationproject.domain.DTO.MemberDTO;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Tiers;
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
        memberRepository.save(member);
    }

    /*
    * 회원 가입시
    * 아이디, 닉네임
    * 중복 확인
    */
    @Transactional(readOnly = true)
    public boolean validateDuplicatedNickName(String nickname) {
        return !memberRepository.findByNickName(nickname).isEmpty();
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }

    public MemberDTO research(Long id) {
        Member member = findById(id);
        MemberDTO findMember = new MemberDTO();
        findMember.setMember(member);

        return findMember;
    }

    //== create logic ==//
    public Member createMember(MemberForm form) {
        Member member = new Member();
        member.create(form.getId(), form.getEmail(), form.getNickName(),
                form.getGender(), form.getDistrict(), form.getAge_range(), form.getFavoriteCategory());

        return member;
    }

    public void updateMember(Long userId, MemberForm myInfo) {
        Member member = findById(userId);
        member.update(myInfo);
    }
}
