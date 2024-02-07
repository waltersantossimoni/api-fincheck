package com.fincheck.apifincheck.configuration;

import com.fincheck.apifincheck.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
  private final UserRepository userRepository;

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findById(UUID.fromString(username))
      .orElseThrow(() -> new RuntimeException("No such user"));
  }
}
