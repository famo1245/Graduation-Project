package meundi.graduationproject.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import meundi.graduationproject.domain.DTO.MemberForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    /* 사용자의 등급 점수 */
    private Integer tierScore;

    @OneToMany(mappedBy = "member")
//    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @Column(length = 1000)
    private String likedCulture;

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
        this.tierScore = 0;
    }

    //==update logic==//
    public void update(MemberForm form) {
        this.nickName = form.getNickName();
        this.district = form.getDistrict();
        this.favoriteCategory = form.getFavoriteCategory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId()) && Objects.equals(getEmail(),
                member.getEmail()) && Objects.equals(getNickName(),
                member.getNickName()) && Objects.equals(getGender(),
                member.getGender()) && Objects.equals(getAge_range(),
                member.getAge_range()) && Objects.equals(getDistrict(),
                member.getDistrict()) && Objects.equals(getFavoriteCategory(),
                member.getFavoriteCategory()) && getTiers() == member.getTiers();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getNickName(), getGender(),
                getAge_range(), getDistrict(), getFavoriteCategory(), getTiers());
    }

    public void plusTierScore(int score){
        this.tierScore += score;
        if (this.tierScore >= 1000){
            this.tiers = Tiers.DIAMOND;
        } else if (this.tierScore > 500) {
            this.tiers = Tiers.GOLD;
        } else if (this.tierScore > 300) {
            this.tiers = Tiers.SILVER;
        } else if (this.tierScore >= 100) {
            this.tiers = Tiers.BRONZE;
        }
    }

    public List<Long> getLikedCulture() {
        List<Long> culture = new ArrayList<>();
        if (this.likedCulture == null || this.likedCulture.isEmpty()) {
            return culture;
        }

        String[] temp = this.likedCulture.split(", ");
        for (String s : temp) {
            culture.add(Long.parseLong(s));
        }
        return culture;
    }

    public void addLikedCulture(Long id) {
        List<Long> likeCulture = getLikedCulture();
        likeCulture.add(id);
        String result = "";
        for (int i=0; i<likeCulture.size(); i++) {
            result += likeCulture.get(i).toString();

            if (i == likeCulture.size() - 1) {
                continue;
            }

            result += ", ";
        }

        this.likedCulture = result;
    }

    public void removeLikedCulture(Long id) {
        List<Long> likeCulture = getLikedCulture();
        likeCulture.remove(id);
        String result = "";
        for (int i=0; i<likeCulture.size(); i++) {
            result += likeCulture.get(i).toString();

            if (i == likeCulture.size() - 1) {
                continue;
            }

            result += ", ";
        }

        this.likedCulture = result;
    }
}
