package meundi.graduationproject.domain;

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
public class Messages {

    private String text;
    private Date created_at;
    private String timeStamp;

    public void setCreated_at(Date date) {
        this.created_at = date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd일 HH:mm");
        this.timeStamp = format.format(this.created_at);
    }
}
