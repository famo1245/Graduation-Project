package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessagesDTO {

    private String text;
    private Date created_at;
    private Long author;
    private String timeStamp;
    private String roomId;

    public void setCreated_at(Date date) {
        this.created_at = date;
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
        this.timeStamp = format.format(this.created_at);
    }
}
