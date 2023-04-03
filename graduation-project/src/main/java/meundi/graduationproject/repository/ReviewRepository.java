package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
}
