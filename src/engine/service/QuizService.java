package engine.service;

import engine.domain.exception.ForbiddenException;
import engine.domain.exception.NotFoundException;
import engine.domain.request.QuizRequest;
import engine.domain.response.QuizInfoResponse;
import engine.domain.response.QuizResponse;
import engine.domain.response.QuizSolveResponse;
import engine.model.Answer;
import engine.model.Option;
import engine.model.Quiz;
import engine.model.QuizInfo;
import engine.repository.QuizInfoRepository;
import engine.repository.QuizRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

  private static final String WRONG_MESSAGE = "Wrong answer! Please, try again.";
  private static final String SUCCESS_MESSAGE = "Congratulations, you're right!";
  private static final String NOT_FOUND_MESSAGE = "Not Found";

  private final QuizRepository quizRepository;

  private final QuizInfoRepository quizInfoRepository;

  public QuizSolveResponse solveQuiz(List<Integer> answer, long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
    if (optionalQuiz.isEmpty()) {
      throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
    Quiz quiz = optionalQuiz.get();

    if (Objects.isNull(quiz.getAnswer()) && answer.isEmpty()) {
      QuizInfo quizInfo = QuizInfo.builder()
          .quizId(quiz.getId())
          .completedAt(LocalDateTime.now())
          .username(authentication.getName())
          .build();
      quizInfoRepository.save(quizInfo);
      return QuizSolveResponse.builder().success(true)
          .feedback(SUCCESS_MESSAGE).build();
    }

    if (Objects.isNull(quiz.getAnswer()) && !answer.isEmpty()) {
      return QuizSolveResponse.builder().success(false)
          .feedback(WRONG_MESSAGE).build();
    }

    List<Integer> answerNumbers = quiz.getAnswer().stream().map(Answer::getAnswer)
        .collect(Collectors.toList());
    if (answerNumbers.equals(answer)) {
      QuizInfo quizInfo = QuizInfo.builder()
          .quizId(quiz.getId())
          .completedAt(LocalDateTime.now())
          .username(authentication.getName())
          .build();
      quizInfoRepository.save(quizInfo);
      return QuizSolveResponse.builder().success(true)
          .feedback(SUCCESS_MESSAGE).build();
    }

    return QuizSolveResponse.builder().success(false)
        .feedback(WRONG_MESSAGE).build();
  }

  public QuizResponse createQuiz(QuizRequest quizRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Quiz quiz = Quiz.builder()
        .text(quizRequest.getText())
        .title(quizRequest.getTitle())
        .user(authentication.getName()).build();

    List<Option> options = quizRequest.getOptions().stream().map(option ->
        Option.builder()
            .option(option)
            .quiz(quiz).build()).collect(Collectors.toList());
    quiz.setOptions(options);

    if (Objects.nonNull(quizRequest.getAnswer())) {
      List<Answer> answers = quizRequest.getAnswer().stream().map(answer ->
          Answer.builder().answer(answer)
              .quiz(quiz).build()).collect(Collectors.toList());
      quiz.setAnswer(answers);
    }

    Quiz savedQuiz = quizRepository.save(quiz);
    return mapQuizToQuizResponse(savedQuiz);
  }

  public QuizResponse getQuiz(long id) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
    if (optionalQuiz.isEmpty()) {
      throw new NotFoundException(NOT_FOUND_MESSAGE);
    }

    Quiz quiz = optionalQuiz.get();
    return mapQuizToQuizResponse(quiz);
  }

  public Page<QuizResponse> getQuizzes(Pageable pageable) {
    return quizRepository.findAll(PageRequest.of(pageable.getPageNumber(), 10))
        .map(this::mapQuizToQuizResponse);
  }

  public void deleteQuiz(long id) {
    Optional<Quiz> optionalQuiz = quizRepository.findById(id);
    if (optionalQuiz.isEmpty()) {
      throw new NotFoundException(NOT_FOUND_MESSAGE);
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Quiz quiz = optionalQuiz.get();
    if (!authentication.getName().equals(quiz.getUser())) {
      throw new ForbiddenException("The specified user is not the author of this quiz");
    }
    quizRepository.delete(quiz);
  }

  public Page<QuizInfoResponse> getQuizInfos(Pageable pageable) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return quizInfoRepository.findByUsername(authentication.getName(),
            PageRequest.of(pageable.getPageNumber(),
                10,
                Sort.by("completedAt").descending()))
        .map(this::mapQuizInfoToQuizInfoResponse);
  }

  private QuizResponse mapQuizToQuizResponse(Quiz quiz) {
    QuizResponse.QuizResponseBuilder quizResponseBuilder = QuizResponse.builder()
        .id(quiz.getId())
        .text(quiz.getText())
        .title(quiz.getTitle());

    List<String> optionTexts = quiz.getOptions().stream().map(Option::getOption)
        .collect(Collectors.toList());
    quizResponseBuilder.options(optionTexts);

    if (Objects.nonNull(quiz.getAnswer())) {
      List<Integer> answerNumbers = quiz.getAnswer().stream().map(Answer::getAnswer)
          .collect(Collectors.toList());
      quizResponseBuilder.answer(answerNumbers);
    }
    return quizResponseBuilder.build();
  }

  private QuizInfoResponse mapQuizInfoToQuizInfoResponse(QuizInfo quizInfo) {
    QuizInfoResponse quizInfoResponse = QuizInfoResponse.builder()
        .id(quizInfo.getQuizId())
        .completedAt(quizInfo.getCompletedAt())
        .build();
    return quizInfoResponse;
  }
}
