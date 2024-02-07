package com.fincheck.apifincheck.service.implementation;

import com.fincheck.apifincheck.model.User;
import com.fincheck.apifincheck.repository.UserRepository;
import com.fincheck.apifincheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {
  private final UserRepository userRepository;

  @Override
  public Optional<User> getUserById(UUID userId) {
    return userRepository.findById(userId);
  }
}
