package meundi.graduationproject.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.friend.DTO.FriendInsertDTO;
import meundi.graduationproject.domain.friend.DTO.FriendSearchDTO;
import meundi.graduationproject.domain.friend.Friend;
import meundi.graduationproject.repository.CultureRepositoryUsingJPA;
import meundi.graduationproject.repository.FriendRepository;
import meundi.graduationproject.repository.MemberRepositoryUsingJPA;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final MemberRepositoryUsingJPA memberRepository;
    private final CultureRepositoryUsingJPA cultureRepository;
    private final EntityManager entityManager;

    public Friend InsertFriend(FriendInsertDTO friendInsertDTO) {
        Friend friend = new Friend();
        friend.CreatNewFriend(friendInsertDTO.getTitle(),
            friendInsertDTO.getContents(), friendInsertDTO.getNum(),
            friendInsertDTO.getCultureTitle(), friendInsertDTO.getMeatTime());
        return friendRepository.save(friend);
    }


    public List<Friend> SearchFriend(FriendSearchDTO friendSearchDTO) {
        List<Friend> result;
        if (StringUtils.hasText((friendSearchDTO.getTotal()))) {
            result = friendRepository.findByTitleContaining(friendSearchDTO.getTotal());
            List<Culture> cultureList = cultureRepository.findByTitleContaining(
                friendSearchDTO.getTotal());
            for (Culture culture : cultureList) {
                result.addAll(friendRepository.findByCultureLike(culture));
            }
            List<Member> memberList = memberRepository.findByNickNameContaining(
                friendSearchDTO.getTotal());
            for (Member member : memberList) {
                result.addAll(friendRepository.findByMemberLike(member));
            }
        } else {
            TypedQuery<Friend> query = entityManager.createQuery("select f from Friend f", Friend.class);
            result = query.getResultList();
        }
        //language=JPAQL
        //중복제거 후 내보내기
        return result.stream().distinct().collect(Collectors.toList());
    }
}
