package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Culture;

import meundi.graduationproject.domain.Review;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CultureRepository {

    private final EntityManager em;

    public void save(Culture culture) {
        em.persist(culture);
    }
    public Culture findOne(Long id) {
        return em.find(Culture.class, id);
    }

    public Culture findByName(String title) {
        return em.createQuery("select c from Culture c where c.title= :title", Culture.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public List<Culture> findAll() {
        return em.createQuery("select c from Culture c", Culture.class)
                .getResultList();
    }
}
