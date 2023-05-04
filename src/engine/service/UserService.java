package engine.service;

import engine.domain.exception.BadRequestException;
import engine.domain.request.UserRequest;
import engine.model.User;
import engine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public void registerUser(UserRequest userRequest) {
    boolean existsByEmail = userRepository.existsByEmail(userRequest.getEmail());
    if (existsByEmail) {
      throw new BadRequestException("This is email is used by another user.");
    }

    User user = User.builder()
        .email(userRequest.getEmail())
        .password(passwordEncoder.encode(userRequest.getPassword()))
        .build();
    userRepository.save(user);
  }
}
