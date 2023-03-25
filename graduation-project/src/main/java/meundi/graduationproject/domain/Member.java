package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Member {
    private String id;  //unique
    private String email;
    private String nickName;    //unique
    private String gender;
    private String age_range;
    private String district;
    private String favoriteCategory;    // 일단 1개 받기
    private Tiers tiers;
}
