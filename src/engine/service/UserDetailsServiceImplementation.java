package engine.service;

import engine.domain.security.UserDetailsImplementation;
import engine.model.User;
import engine.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImplementation implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(username);
    if(user.isEmpty()){
      throw new UsernameNotFoundException("Not found");
    }

    return UserDetailsImplementation.builder()
        .username(user.get().getEmail())
        .password(user.get().getPassword()).build();
  }
}
