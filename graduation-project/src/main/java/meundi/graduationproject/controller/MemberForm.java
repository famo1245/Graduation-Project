package meundi.graduationproject.controller;

import lombok.Getter;
import lombok.Setter;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Tiers;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MemberForm {
    private Long id;  //unique
    private String email;
    private String nickName;    //unique
    private String gender;
    private String age_range;
    private String district;
    private String favoriteCategory;
    private Tiers tiers;

    //선호 카테고리를 배열로 반환해주는 함수
    public List<String> getFavoriteCategoryList() {
        return new ArrayList<>(List.of(favoriteCategory.split(",")));
    }

    public void setMember(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickName = member.getNickName();
        this.gender = member.getGender();
        this.age_range = member.getAge_range();
        this.district = member.getDistrict();
        this.favoriteCategory = member.getFavoriteCategory();
        this.tiers = member.getTiers();
    }
}
