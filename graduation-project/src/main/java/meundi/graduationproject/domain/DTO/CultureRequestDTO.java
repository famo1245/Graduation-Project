package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CultureRequestDTO {
    private Long id;
    private String contents;
    private String title;

}
