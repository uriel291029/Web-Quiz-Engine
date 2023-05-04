package engine.controller;

import engine.domain.exception.BadRequestException;
import engine.domain.exception.ForbiddenException;
import engine.domain.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  protected ResponseEntity<Object> handleResourceNotFoundException(Exception ex) {
    return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadRequestException.class)
  protected ResponseEntity<Object> handleBadRequestException(Exception ex) {
    return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ForbiddenException.class)
  protected ResponseEntity<Object> handleForbiddenException(Exception ex) {
    return new ResponseEntity<>(ex, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleException(Exception ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
