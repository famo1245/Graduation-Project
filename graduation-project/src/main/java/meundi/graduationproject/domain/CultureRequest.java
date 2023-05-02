package meundi.graduationproject.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
public class CultureRequest {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @NotBlank
    @Column(name = "request_content")
    private String contents;
}
