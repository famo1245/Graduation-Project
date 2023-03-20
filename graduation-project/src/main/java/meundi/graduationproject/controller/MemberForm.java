package meundi.graduationproject.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    private String id;  //unique
    private String password;    //encryption
    private String nickName;    //unique
    private String gender;
    private int age;
    private String district;
    private String favoriteCategory;    // 일단 1개 받기
}
