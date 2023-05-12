package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Review;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    /*findByCultureId, findAll과 findOne이 가져오는 데이터가 상이함*/

    private final EntityManager em;

    public void save(Review review) {
        em.persist(review);
    }
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

    public List<Review> SearchReview(ReviewSearch reviewSearch) {
        //language=JPAQL
        String jpql = "select r from Review r join r.culture c";
        // 문화 이름으로 검색 결과
        if (StringUtils.hasText(reviewSearch.getTotal())) {
            jpql += " where c.title like :title";
            jpql += " or r.reviewTitle like :reviewTitle";
        }
        TypedQuery<Review> query1 = em.createQuery(jpql, Review.class);
        if (StringUtils.hasText(reviewSearch.getTotal())){
            query1.setParameter("title", reviewSearch.getTotal());
            query1.setParameter("reviewTitle", reviewSearch.getTotal());
        }
        List<Review> resultList1 = query1.getResultList();
        //중복제거 후 내보내기
        return resultList1.stream().distinct().collect(Collectors.toList());




//        //리뷰 제목으로 검색 결과
//        if (StringUtils.hasText(reviewSearch.getTotal())) {
//            jpql += " and r.reviewTitle like :reviewTitle";
//        }
//        TypedQuery<Review> query2 = em.createQuery(jpql, Review.class);
//        if (StringUtils.hasText(reviewSearch.getTotal())){
//            query2.setParameter("reviewTitle", reviewSearch.getTotal());
//        }
//        List<Review> resultList2 = query2.getResultList();
//
//        // 합치기
//        resultList1.addAll(resultList2);

    }
}
