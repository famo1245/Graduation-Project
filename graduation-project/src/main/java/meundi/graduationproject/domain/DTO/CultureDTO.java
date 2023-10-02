package meundi.graduationproject.domain.DTO;

import lombok.Getter;
import lombok.Setter;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.Review;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class CultureDTO {
    private Long id;
    /*문화/공연 제목*/
    private String title;
    /*출연자*/
    private String player;
    /*홈페이지 주소(url)*/
    private String org_link;
    /*포스터 (url)*/
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
    private Date endDate;
    private String isFree;
    private List<ReviewDTO> reviews;

    // initialization
    public void setCultureDTO(Culture c) {
        this.id = c.getId();
        this.title = c.getTitle();
        this.player = c.getPlayer();
        this.org_link = c.getOrg_link();
        this.main_img = c.getMain_img();
        this.guname = c.getGuname();
        this.Date = c.getDate();
        this.rgstDate = c.getRgstDate();
        this.codeName = c.getCodeName();
        this.use_trgt = c.getUse_trgt();
        this.place = c.getPlace();
        this.endDate = c.getEndDate();
        this.reviews = new ArrayList<>();
        for(Review r : c.getReviews()) {
            ReviewDTO temp = new ReviewDTO();
            temp.setReviewDTO(r, r.getMember().getNickName(), r.getCulture().getMain_img());
            this.reviews.add(temp);
        }
    }
}
