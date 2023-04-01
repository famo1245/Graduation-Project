package meundi.graduationproject.service;

import lombok.RequiredArgsConstructor;
import meundi.graduationproject.domain.Culture;
import meundi.graduationproject.repository.CultureRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CultureService {
    private final CultureRepository cultureRepository;

    public Culture insertCulture(Culture culture) {
        cultureRepository.save(culture);
        return culture;
    }
    public List<Culture> findCultureAll() {
        return cultureRepository.findAll();
    }

    public Culture findOne(Long id) {
        return cultureRepository.findOne(id);
    }

    public Culture findOneByTitle(String title) {
        return cultureRepository.findByName(title);
    }
}
