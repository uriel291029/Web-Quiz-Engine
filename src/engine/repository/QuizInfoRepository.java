package engine.repository;

import engine.model.QuizInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizInfoRepository extends JpaRepository<QuizInfo, Long> {

  Page<QuizInfo> findByUsername(String username, Pageable pageable);
}
