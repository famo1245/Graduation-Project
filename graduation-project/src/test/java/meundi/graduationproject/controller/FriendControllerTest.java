package meundi.graduationproject.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import meundi.graduationproject.domain.friend.DTO.FriendInsertDTO;
import meundi.graduationproject.domain.friend.DTO.FriendSearchDTO;
import meundi.graduationproject.domain.friend.Friend;
import meundi.graduationproject.service.FriendService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc
class FriendControllerTest {
    @Autowired
    FriendService friendService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @DisplayName("Friend 객체 리스트를")
    @Nested
    class test1 {
        @DisplayName("내보낼 수 있다.")
        @Test
        void success() throws Exception {
            //given
            FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            friendService.InsertFriend(dto);
            //when, then
            mockMvc.perform(get("/friend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
        }
        @DisplayName("검색을 통해 내보낼 수 있다.")
        @Test
        void successBySearch()throws Exception{
            //given
            FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            friendService.InsertFriend(dto);
            //when then
            FriendSearchDTO friendSearchDTO = new FriendSearchDTO("testTitle");
            String content = om.writeValueAsString(friendSearchDTO);
            mockMvc.perform(post("/friend")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
        }
        @DisplayName("검색 키워드가 빈칸이기때문에 모든 객체를 내보낸다.")
        @Test
        void SuccessSearchBlank()throws Exception{
            //given
            FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            friendService.InsertFriend(dto);
            //when then
            FriendSearchDTO friendSearchDTO = new FriendSearchDTO(" ");
            String content = om.writeValueAsString(friendSearchDTO);
            mockMvc.perform(post("/friend")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
        }
        @DisplayName("검색 키워드가 null 값이기 때문에 모든 객체를 내보낸다.")
        @Test
        void SuccessSearchNull()throws Exception{
            //given
            FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            friendService.InsertFriend(dto);
            //when then
            FriendSearchDTO friendSearchDTO = new FriendSearchDTO(" ");
            String content = om.writeValueAsString(friendSearchDTO);
            mockMvc.perform(post("/friend")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
        }
        @DisplayName("검색 키워드에 맞는 객체가 없어 내보낼 수 없다.")
        @Test
        void FailSearchBlank()throws Exception{
            //given
            FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            friendService.InsertFriend(dto);
            //when then
            FriendSearchDTO friendSearchDTO = new FriendSearchDTO("by");
            String content = om.writeValueAsString(friendSearchDTO);
            mockMvc.perform(post("/friend")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        }
    }
    @DisplayName("Friend 객체를")
    @Nested
    class test2{
        @DisplayName("생성할 수 있다.")
        @Test
        void Success() throws Exception {
            //given
            FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            //when then
            String content = om.writeValueAsString(dto);
            mockMvc.perform(post("/friend/creat")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(status().isOk())
                .andDo(print());
        }
        @DisplayName("양식에 맞지 않아 생성할 수 없다.")
        @Test
        void Fail() throws Exception {
            //given
            FriendInsertDTO dto = new  FriendInsertDTO("testTitle", " ", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            //when then
            String content = om.writeValueAsString(dto);
            mockMvc.perform(post("/friend/creat")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("must not be blank"))
                .andDo(print());
        }
        @DisplayName("확인할 수 있다.")
        @Test
        void successByDetail() throws Exception {
            //given
            FriendInsertDTO dto = new FriendInsertDTO("testTitle", "testContents", 3,
                "[M클래식 축제] 마포공연예술관광페스티벌 [타케자와 유토 피아노 리사이틀]", "20230808 12:03");
            Friend friend = friendService.InsertFriend(dto);
            //when then
            mockMvc.perform(get("/friend/{friend_id}",friend.getId()))
                .andExpect(status().isOk())
                .andDo(print());
        }
    }

}