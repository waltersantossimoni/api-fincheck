package com.fincheck.apifincheck.controller;

import com.fincheck.apifincheck.dto.UserDTO;
import com.fincheck.apifincheck.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping("/me/{userId}")
  public ResponseEntity<UserDTO> me(@PathVariable UUID userId) {
    UserDTO user = userService.findUserById(userId);

    return ResponseEntity.ok().body(user);
  }
}
