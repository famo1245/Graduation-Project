package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class ChatRoomDTO {

    private String roomId;
    private Long authorId;
    private String authorNickname;
    private String meetDate;
    private Map<String, Boolean> participants = new HashMap<>();
    private Boolean isUpdated = false;
    private String availableAgeRange;
    private String title;
    private String gender;
    private Long cultureId;
    private String cultureImg;
    private String cultureTitle;
    private int max;

    // business logic
    public void addParticipants(Long id) {
        this.participants.put(id.toString(), false);
    }
}