package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatDTO {

    private Long id;
    private LocalDateTime chattingDateTime;
    private String chattingContents;

}
