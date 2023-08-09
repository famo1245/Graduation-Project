package meundi.graduationproject.domain.friend.DTO;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FriendSearchDTO {

    private String total;

    public FriendSearchDTO(String total) {
        this.total = total;
    }
    public FriendSearchDTO(){}
}
