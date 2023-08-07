package meundi.graduationproject.repository;

import lombok.Getter;
import lombok.Setter;

//Search 값 넘겨주기 위해서
@Getter
@Setter
public class CultureRequestSearch {
    private String total;
    private String requestTitle;
    private String MemberNickname;
}
