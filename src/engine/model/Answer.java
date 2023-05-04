package engine.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

  @Id
  @GeneratedValue
  private Long answerId;

  private Integer answer;

  @ManyToOne
  @JoinColumn(name = "id")
  private Quiz quiz;
}
