package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.CultureRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CultureRequestRepository {

    private final EntityManager em;

    public void saveRequest(CultureRequest cultureRequest){
        em.persist(cultureRequest);
    }
    public void deleteRequest(CultureRequest cultureRequest){
        em.remove(cultureRequest);
    }
    public CultureRequest findRequest(Long id){
        return em.find(CultureRequest.class, id);
    }

    public CultureRequest findOne(Long id) {
        return em.find(CultureRequest.class, id);
    }

    public List<CultureRequest> findAll() {
        return em.createQuery("select r from CultureRequest r", CultureRequest.class)
                .getResultList();
    }
}
