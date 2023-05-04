package engine.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

  private Long id;

  private String title;

  private String text;

  private List<String> options;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Integer> answer;
}
