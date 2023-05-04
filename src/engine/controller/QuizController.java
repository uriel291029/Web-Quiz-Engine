package engine.controller;

import engine.domain.request.AnswerRequest;
import engine.domain.request.QuizRequest;
import engine.domain.response.QuizInfoResponse;
import engine.domain.response.QuizResponse;
import engine.domain.response.QuizSolveResponse;
import engine.service.QuizService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/quizzes")
public class QuizController {

  @Autowired
  private QuizService quizService;

  @PostMapping
  public QuizResponse createQuiz(@Valid @RequestBody QuizRequest quizRequest) {
    return quizService.createQuiz(quizRequest);
  }

  @GetMapping("{id}")
  public QuizResponse getQuiz(@PathVariable long id) {
    return quizService.getQuiz(id);
  }

  @GetMapping("completed")
  public Page<QuizInfoResponse> getQuizInfos(Pageable pageable) {
    return quizService.getQuizInfos(pageable);
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("{id}")
  public void deleteQuiz(@PathVariable long id) {
    quizService.deleteQuiz(id);
  }

  @PostMapping("{id}/solve")
  public QuizSolveResponse solveQuiz(@PathVariable long id,
      @RequestBody AnswerRequest answerRequest) {
    return quizService.solveQuiz(answerRequest.getAnswer(), id);
  }

  @GetMapping
  public Page<QuizResponse> getQuizzes(Pageable pageable) {
    return quizService.getQuizzes(pageable);
  }
}
