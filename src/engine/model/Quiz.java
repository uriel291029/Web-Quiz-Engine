package engine.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

  @Id
  @GeneratedValue
  private Long id;

  private String title;

  private String text;

  @Exclude
  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
  private List<Option> options;

  @Exclude
  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
  private List<Answer> answer;

  private String user;
}
