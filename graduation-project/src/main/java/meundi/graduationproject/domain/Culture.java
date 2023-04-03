package meundi.graduationproject.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Culture {
    public Culture() {
    }

    @Id @GeneratedValue
    @Column(name = "culture_id")
    private Long id;
    /*문화/공연 제목*/
    private String title;
    /*출연자*/
    private String player;
    /*홈페이지 주소(url)*/
    @Column(length = 500)
    private String org_link;
    /*포스터 (url)*/
    @Column(length = 500)
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
    /*시작일*//*
    private LocalDateTime startDate;
    *//*종료일*//*
    private LocalDateTime endDate;*/

    /*분류 총 16개
     * 문화교양/강좌,전시/미술,뮤지컬/오페라,기타,연극,무용,영화,
     * 국악,콘서트,축제-문화/예술,축제-전통/역사,축제-시민화합,
     * 클래식,축제-기타,축제-자연/경관,독주/독창회
     * */

    // 생성 매서드 //
    public void InsertCultureFromJson(String title, String player,
                   String org_link, String main_img, String guname,
                   String date, String rgstDate, String codeName,
                   String use_trgt, String place) {
        this.title = title;
        this.player = player;
        this.org_link = org_link;
        this.main_img = main_img;
        this.guname = guname;
        Date = date;
        this.rgstDate = rgstDate;
        this.codeName = codeName;
        this.use_trgt = use_trgt;
        this.place = place;
    }
}
