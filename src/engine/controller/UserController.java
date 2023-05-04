package engine.controller;


import engine.domain.request.UserRequest;
import engine.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class UserController {

  private final UserService userService;

  @PostMapping("register")
  public void registerUser(@Valid @RequestBody UserRequest userRequest) {
    userService.registerUser(userRequest);
  }
}
