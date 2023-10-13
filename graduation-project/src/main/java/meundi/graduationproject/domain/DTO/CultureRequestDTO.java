package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;
import meundi.graduationproject.domain.CultureRequest;

import java.time.LocalDateTime;

@Getter
@Setter
public class CultureRequestDTO {
    private Long id;
    private String contents;
    private String title;
    private String nickname;

    // business logic
    public void setDTO(CultureRequest c) {
        this.id = c.getId();
        this.contents = c.getContents();
        this.title = c.getTitle();
        this.nickname = c.getMember().getNickName();
    }
}
