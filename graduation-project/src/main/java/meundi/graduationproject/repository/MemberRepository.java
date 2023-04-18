package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByNickName(String nickName) {
        return em.createQuery("select m from Member m where m.nickName= :nickName", Member.class)
                .setParameter("nickName", nickName)
                .getResultList();
    }
}
