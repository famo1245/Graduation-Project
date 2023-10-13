package meundi.graduationproject.repository;

import meundi.graduationproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepositoryUsingJPA extends JpaRepository<Member,Long> {
    //select * from Member WHERE nickName LIKE "%nickName%"
    List<Member> findByNickNameContaining(String nickname);
    Member findByIdEquals(Long id);
}
