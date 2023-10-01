package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ChatRoomDTO {

    private String roomId;
    private Long authorId;
    private Map<String, Boolean> participants = new HashMap<>();
    private Boolean isUpdated = false;
    private String availableAgeRange;
    private String title;
    private Long cultureId;
    private String cultureImg;
    private String cultureTitle;
    private int max;

    // business logic
    public void addParticipants(Long id) {
        this.participants.put(id.toString(), false);
    }
}