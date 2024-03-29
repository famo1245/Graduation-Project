package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Culture;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CultureRepository {

    private final EntityManager em;

    public void save(Culture culture) {
        em.persist(culture);
    }
    public Culture findOneById(Long id) {
        return em.find(Culture.class, id);
    }

    public Optional<Culture> findByTitle(String title) {
        List<Culture> cultures= em.createQuery("select c from Culture c where c.title= :title", Culture.class)
                .setParameter("title", title)
                .getResultList();
        return cultures.stream().findAny();
    }

    public List<Culture> findAll() {
        return em.createQuery("select c from Culture c", Culture.class)
                .getResultList();
    }

    // 마지막으로 저장한 값을 찾기
    public Culture findLastOne() {
        List<Culture> cultures = findAll();
        int lastIndex = cultures.size() - 1;
        return cultures.get(lastIndex);
    }

    // 대분류로 찾기
    // 최신 5개를 반환
    public List<Culture> findByCategory(String category) {
        return em.createQuery("select c from Culture c where c.codeName= :category", Culture.class)
                .setParameter("category", category)
                .getResultList();
    }

    /**
     * 문화 리스트 페이지에서
     * 한 페이지 별로 10개 정도 끊어서
     * 보여주기 위한 메서드 (미완)
     * @param startId
     * @param endId
     * @return List<Culture>
     */
    /*    public List<Culture> findByIdBetween(long startId, long endId) {
        return em.createQuery("select c from Culture c where c.id between ?1 and ?2", Culture.class)
                .setParameter("?1", startId)
                .setParameter("?2", endId)
                .getResultList();

    }*/
}
