package meundi.graduationproject.domain;

import lombok.Getter;
import meundi.graduationproject.controller.MemberForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @Column(name = "member_id")
    /* 사용자의 id, api에서 넘어 옴, generated 쓰지 말것 */
    private Long id;
    /* 사용자의 email 주소 api에서 넘어 옴 */
    private String email;
    /* 닉네임 unique */
    @Column(unique = true)
    private String nickName;
    /* 성별 */
    private String gender;
    /* 나이대, 범위로 api에서 제공 */
    private String age_range;
    /* 사용자의 관심 지역 */
    private String district;
    /* 사용자의 관심 카테고리 */
    private String favoriteCategory;
    /* 사용자의 등급, 이름은 추후 정할 것 */
    @Enumerated(EnumType.STRING)
    private Tiers tiers;

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    //==create logic==//
    public void create(Long id, String email, String nickName, String gender, String district, String age_range,
                               String favoriteCategory) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.gender = gender;
        this.district = district;
        this.age_range = age_range;
        this.favoriteCategory = favoriteCategory;
        this.tiers = Tiers.BRONZE;
    }

    //==update logic==//
    public void update(MemberForm form) {
        this.nickName = form.getNickName();
        this.district = form.getDistrict();
        this.favoriteCategory = form.getFavoriteCategory();
    }
}
