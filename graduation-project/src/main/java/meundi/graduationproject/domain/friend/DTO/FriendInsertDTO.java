package meundi.graduationproject.domain.friend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class FriendInsertDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    /* 인원 수 */
    @Range(min = 2, max= 10)
    private int num;

    private String cultureTitle;

    private String meatTime;

    public FriendInsertDTO(String title, String contents, int num, String cultureTitle,
        String meatTime) {
        this.title = title;
        this.contents = contents;
        this.num = num;
        this.cultureTitle = cultureTitle;
        this.meatTime = meatTime;
    }
}
