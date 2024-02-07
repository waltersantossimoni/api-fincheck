package com.fincheck.apifincheck.controller;

import com.fincheck.apifincheck.model.User;
import com.fincheck.apifincheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserController {
  private final UserService userService;

  @GetMapping("/me")
  public Optional<User> me(UUID userId){
    return userService.getUserById(userId);
  }
}
