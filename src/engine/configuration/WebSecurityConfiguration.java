package engine.configuration;

import engine.service.UserDetailsServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserDetailsServiceImplementation UserDetailsServiceImplementation;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers("/api/register").permitAll()
        .mvcMatchers("/api/quizzes/**").authenticated()
        .anyRequest().permitAll()
        .and()
        .csrf().disable().headers().frameOptions().disable().and()
            .httpBasic();
    //http.httpBasic();
    //http.csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(UserDetailsServiceImplementation)
        .passwordEncoder(getEncoder());
    auth
        .inMemoryAuthentication() // user store 2
        .withUser("Admin").password("hardcoded").roles("USER")
        .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
  }

  @Bean
  public PasswordEncoder getEncoder() {
    return new BCryptPasswordEncoder();
  }
}
