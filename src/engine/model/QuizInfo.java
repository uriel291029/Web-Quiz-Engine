package engine.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizInfo {

  @Id
  @GeneratedValue
  private Long id;

  private Long quizId;

  private LocalDateTime completedAt;

  private String username;
}
