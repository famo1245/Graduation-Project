package meundi.graduationproject.repository;

import java.util.List;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendRepository extends JpaRepository<Friend,Long> {

    List<Friend> findByMemberLike(Member member);

    List<Friend> findByTitleContaining(String title);

    List<Friend> findByCultureLike(Culture culture);

    @Override
    <S extends Friend> S save(S entity);

}
