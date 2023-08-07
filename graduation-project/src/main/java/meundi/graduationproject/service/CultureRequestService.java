package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.CultureRequest;
import meundi.graduationproject.repository.CultureRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CultureRequestService {
    private final CultureRequestRepository cultureRequestRepository;

    public CultureRequest insertCultureRequest(CultureRequest request){
        cultureRequestRepository.saveRequest(request);
        return request;
    }

    public void deleteCultureRequest(CultureRequest request){
        cultureRequestRepository.deleteRequest(request);
    }

    /*
    public void updateCultureRequest(Long id, String title, int grade, String content){
        CultureRequest find = cultureRequestRepository.findOne(id);
    }
    */

    //전체 list로 보여주기
    public List<CultureRequest> findRequestAll(){
        return cultureRequestRepository.findAll();
    }
    //하나 보여주기
    public CultureRequest findOne(Long id){
        return cultureRequestRepository.findOne(id);
    }
}
