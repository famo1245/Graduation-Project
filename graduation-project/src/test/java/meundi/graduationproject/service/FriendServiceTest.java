package meundi.graduationproject.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import meundi.graduationproject.domain.friend.DTO.FriendInsertDTO;
import meundi.graduationproject.domain.friend.Friend;
import meundi.graduationproject.repository.FriendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired
    FriendService friendService;
    @Autowired
    FriendRepository friendRepository;

    @DisplayName("Friend 객체를")
    @Nested
    class test1{
        @DisplayName("생성할 수 있다.")
        @Test
        void success(){
            //given
            FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            //when
            Friend friend = friendService.InsertFriend(dto);
            Optional<Friend> testTitle = friendRepository.findById(friend.getId());
            //then
            Assertions.assertThat(friend.getId()).isEqualTo(testTitle.get().getId());
        }
    }

}