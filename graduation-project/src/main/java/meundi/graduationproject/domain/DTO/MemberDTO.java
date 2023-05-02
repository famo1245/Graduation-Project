package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import meundi.graduationproject.controller.MemberForm;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Tiers;

@Getter
public class MemberDTO {
    private Long id;
    private String email;
    private String nickName;
    private String gender;
    private String age_range;
    private String district;
    private String favoriteCategory;
    private Tiers tiers;

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
    public void changeInfo(MemberForm form) {
        this.nickName = form.getNickName();
        this.district = form.getDistrict();
        this.favoriteCategory = form.getFavoriteCategory();
    }
}
