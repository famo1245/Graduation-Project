package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class CultureRequest {

    //누가 요청을 했는지 알기 위해서
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @NotBlank
    @Column(name = "request_content")
    private String contents;

    private LocalDateTime requestDateTime;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateRequest(String requestTitle, String contents){
        this.contents = contents;
        this.title = requestTitle;
    }

}
