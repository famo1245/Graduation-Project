package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Tiers;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MemberForm {
    private Long id;
    private String email;
    private String nickName;
    private String gender;
    private String age_range;
    private String district;
    private String favoriteCategory;
    private Tiers tiers;
    private int tierScore;

    //선호 카테고리를 리스트로 반환하는 함수
    public List<String> getFavoriteCategoryList() {
        if (StringUtils.hasText(favoriteCategory)) {
            return new ArrayList<>(List.of(favoriteCategory.split(",")));
        }

        return null;
    }

    //Member 객체를 이용하여 setting
    public void setMember(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickName = member.getNickName();
        this.gender = member.getGender();
        this.age_range = member.getAge_range();
        this.district = member.getDistrict();
        this.favoriteCategory = member.getFavoriteCategory();
        this.tiers = member.getTiers();
        this.tierScore = member.getTierScore();
    }
}
