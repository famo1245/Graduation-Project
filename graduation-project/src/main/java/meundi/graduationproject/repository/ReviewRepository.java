package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Review;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

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
        // User 검색도 넣어야 하므로 필요
        boolean isFirstCondition = true;
        //문화 제목 검색
        if (StringUtils.hasText(reviewSearch.getCultureTitle())) {
            if (isFirstCondition) {
                jpql +=" where";
                isFirstCondition = false;
            }
            else {
                jpql += " and";
            }
            jpql += " c.title like :title";
        }
        TypedQuery<Review> query = em.createQuery(jpql, Review.class);
        if (StringUtils.hasText(reviewSearch.getCultureTitle())){
            query.setParameter("title", reviewSearch.getCultureTitle());
        }
        return query.getResultList();
    }
}
