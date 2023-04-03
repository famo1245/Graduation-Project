package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    /* 사용자의 id, api에서 넘어 옴 */
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
}
