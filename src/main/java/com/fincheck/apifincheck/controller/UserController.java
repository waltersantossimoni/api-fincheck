package com.fincheck.apifincheck.controller;

import com.fincheck.apifincheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserController {
  private final UserService userService;

  @GetMapping("/me")
  public String me(String userId){
    return userService.getUserById(userId);
  }
}
