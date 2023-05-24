package meundi.graduationproject.repository;

import meundi.graduationproject.domain.Culture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CultureRepositoryUsingJPA  extends JpaRepository<Culture,Long> {
    //select * from Culture WHERE title LIKE "%title%"
    List<Culture> findByTitleContaining(String title);
}
