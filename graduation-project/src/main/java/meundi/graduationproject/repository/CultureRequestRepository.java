package meundi.graduationproject.repository;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.CultureRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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

}
