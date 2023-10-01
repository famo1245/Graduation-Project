package meundi.graduationproject.repository;

import meundi.graduationproject.domain.Culture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CultureRepositoryUsingJPA  extends JpaRepository<Culture,Long> {
    //select * from Culture WHERE title LIKE "%title%"
    List<Culture> findByTitleContaining(String title);

    List<Culture> findByCodeNameContaining(String codeName);

    List<Culture> findByGunameContaining(String guName);

    List<Culture> findByCodeName(String codename);

    List<Culture> findAllByEndDateBetween(Date start, Date end);
}
