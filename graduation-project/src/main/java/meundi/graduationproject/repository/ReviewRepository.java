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

    public Review findOne(Long id) {
        return em.find(Review.class, id);
    }

    public List<Review> findByCultureId(Long cultureId){
        return em.createQuery("select r from Review r where r.cultureId= :cultureId", Review.class)
                .setParameter("cultureId", cultureId)
                .getResultList();

    }

    public List<Review> findAll(){
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }
}
