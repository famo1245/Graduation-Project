package meundi.graduationproject.domain.DTO.response;

import lombok.Getter;
import lombok.Setter;
import meundi.graduationproject.domain.CultureRequest;
import meundi.graduationproject.domain.Member;

@Setter
@Getter
public class CultureRequestResponseDTO {
    CultureRequest requestDetail;
    Member member;
}
