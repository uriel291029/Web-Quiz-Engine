package engine.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizRequest {

  private Long id;

  @NotBlank
  private String title;

  @NotBlank
  private String text;

  @NotNull
  @Size(min = 2)
  private List<String> options;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Integer> answer;
}
