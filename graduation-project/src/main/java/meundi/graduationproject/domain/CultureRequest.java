package meundi.graduationproject.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
public class CultureRequest {

    //누가 요청을 했는지 알기 위해서
    @Id
    @GeneratedValue
    private Long id;

    //제목 : 확실하게 필요하지는 않음
    private String title;

    //컨텐츠는 무조건 필요해서 not blank
    @NotBlank
    @Column(name = "request_content")
    private String contents;

    //요청시 시간으로 우선순위 둘수도 있어서 생성
    private LocalDateTime requestDateTime;


    public void updateRequest(String requestTitle, String contents){
        this.contents = contents;
        this.title = requestTitle;
    }
}
