package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.domain.Member;
import meundi.graduationproject.domain.Review;
import meundi.graduationproject.domain.ReviewComment;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReviewRepository  {
    /*findByCultureId, findAll과 findOne이 가져오는 데이터가 상이함*/

    private final EntityManager em;
    private final CultureRepositoryUsingJPA CRJ;
    private final MemberRepositoryUsingJPA MRJ;
    private final ReviewRepositoryUsingJPA RRJ;


    public void save(Review review) {
        em.persist(review);
    }
    public void deleteReview(Review review){ em.remove(review);}
    /* 리뷰 이름으로 검색*/
    public Review findOne(Long id) {
        return em.find(Review.class, id);
    }

    /*문화 제목으로 검색 */
    public List<Review> findByCultureTitle(String cultureTitle){
        return em.createQuery("select r from Review r where r.cultureTitle= :cultureTitle", Review.class)
                .setParameter("cultureTitle", cultureTitle)
                .getResultList();
    }
    /*모든 리뷰 검색 */
    public List<Review> findAll(){
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }


    //검색 쿼리 -> 키워드가 문화 이름에 포함되거나, 리뷰 이름에 포함되거나, 멤버 이름에 포함되면 해당 리뷰 출력
    public List<Review> SearchReviewTotal(ReviewSearch reviewSearch) {
        List<Review> resultList1;
        if (StringUtils.hasText(reviewSearch.getTotal())) {
            resultList1 = RRJ.findByReviewTitleContaining(reviewSearch.getTotal());
            List<Culture> tempCulture= CRJ.findByTitleContaining(reviewSearch.getTotal());
            List<Member> tempMember = MRJ.findByNickNameContaining(reviewSearch.getTotal());
            for (Culture culture : tempCulture) {
                resultList1.addAll(RRJ.findByCultureLike(culture));
            }
            for (Member member : tempMember) {
                resultList1.addAll(RRJ.findByMemberLike(member));
            }
        }
        else {
            TypedQuery<Review> query = em.createQuery("select r from Review r join r.culture c", Review.class);
            resultList1 = query.getResultList();
        }
        //language=JPAQL
        //중복제거 후 내보내기
        return resultList1.stream().distinct().collect(Collectors.toList());
    }
    // 리뷰 댓글 로직

    //댓글 저장
    public void saveComment(ReviewComment reviewComment){em.persist(reviewComment);}
    public void deleteComment(ReviewComment reviewComment){em.remove(reviewComment);}
    public ReviewComment findComment(Long id){ return em.find(ReviewComment.class, id);}
    //해당 리뷰의 댓글 불러오기
    public List<ReviewComment> findReviewComment(Review review){
        return em.createQuery("select sc from ReviewComment sc where sc.review= :review", ReviewComment.class)
                .setParameter("review",review)
                .getResultList();
    }
    //    public List<Review> SearchReview(ReviewSearch reviewSearch) {
//        //language=JPAQL
//        String jpql = "select r from Review r join r.culture c";
//        // 문화 이름, 리뷰 제목으로 검색 결과
//        if (StringUtils.hasText(reviewSearch.getTotal())) {
//            jpql += " where c.title like %:title%";
//            jpql += " or r.reviewTitle like %:reviewTitle%";
//        }
//        TypedQuery<Review> query1 = em.createQuery(jpql, Review.class);
//        if (StringUtils.hasText(reviewSearch.getTotal())) {
//            query1.setParameter("title", reviewSearch.getTotal());
//            query1.setParameter("reviewTitle", reviewSearch.getTotal());
//        }
//        List<Review> resultList1 = query1.getResultList();
//        //중복제거 후 내보내기
//        return resultList1.stream().distinct().collect(Collectors.toList());
//    }
}
