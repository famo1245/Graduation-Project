package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter @Setter
public class Culture {
    public Culture() {
    }

    public Culture(String title) {
        this.title = title;
    }

    @Id @GeneratedValue
    @Column(name = "culture_id")
    private Long id;
    /*문화/공연 제목*/
    private String title;
    /*출연자*/
    @Column(length = 800)
    private String player;
    /*홈페이지 주소(url)*/
    @Column(length = 400)
    private String org_link;
    /*포스터 (url)*/
    @Column(length = 400)
    private String main_img;
    /*구 이름*/
    private String guname;
    /*일정*/
    private String Date;
    /*신청일*/
    private String rgstDate;
    /*분류*/
    private String codeName;
    /*이용대상*/
    private String use_trgt;
    /*장소*/
    private String place;

    @OneToMany(mappedBy = "culture")
    private List<Review> reviews = new ArrayList<>();

    /* 2023-09-28~2023-09-28 이렇게 data가 들어오는데,
    이를 잘라서 사용해야할 것인지, 시작일 종료일 나누어서 사용할것인지(메소드필요)*/

    /*분류 총 16개
     * 문화교양/강좌,전시/미술,뮤지컬/오페라,기타,연극,무용,영화,
     * 국악,콘서트,축제-문화/예술,축제-전통/역사,축제-시민화합,
     * 클래식,축제-기타,축제-자연/경관,독주/독창회
     * */

    /* 날짜를 이용해야하는 서비스가 생긴다면
    * Date와 rgstDate를 문자열 -> LocalDateTime 변환해야함(formatter or converter 사용)

    * * ++ org_link, main_img URL로 바꾸고 싶은데, 서울시 데이터에서 정확히 http:로 시작 안하는
    * org_link, main_img 가 있어서 익셉션이 터짐 -> 그냥 String으로 뒀음
    * */

    public void InsertCultureFromJson(String title, String player,
                   String org_link, String main_img, String guname,
                   String date, String rgstDate, String codeName,
                   String use_trgt, String place) throws MalformedURLException {

        this.title = title;
        this.player = player;
        this.org_link = org_link;
        this.main_img = main_img;
        this.guname = guname;
        this.Date = date;
        this.rgstDate = rgstDate;
        this.codeName = codeName;
        this.use_trgt = use_trgt;
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Culture)) return false;
        Culture culture = (Culture) o;
        return Objects.equals(getId(), culture.getId()) && Objects.equals(getTitle(),
                culture.getTitle()) && Objects.equals(getPlayer(),
                culture.getPlayer()) && Objects.equals(getOrg_link(),
                culture.getOrg_link()) && Objects.equals(getMain_img(),
                culture.getMain_img()) && Objects.equals(getGuname(),
                culture.getGuname()) && Objects.equals(getDate(),
                culture.getDate()) && Objects.equals(getRgstDate(),
                culture.getRgstDate()) && Objects.equals(getCodeName(),
                culture.getCodeName()) && Objects.equals(getUse_trgt(),
                culture.getUse_trgt()) && Objects.equals(getPlace(), culture.getPlace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPlayer(), getOrg_link(), getMain_img(),
                getGuname(), getDate(), getRgstDate(), getCodeName(), getUse_trgt(), getPlace());
    }
}
