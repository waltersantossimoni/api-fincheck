package com.fincheck.apifincheck.controller;

import com.fincheck.apifincheck.dto.SigninDTO;
import com.fincheck.apifincheck.dto.SignupDTO;
import com.fincheck.apifincheck.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@RestController
public class AuthController {
  private final AuthService authService;

  @PostMapping("/sign-up")
  public ResponseEntity<String> signup(@RequestBody @Valid SignupDTO signupDTO) {
    String accessToken = authService.signup(signupDTO);

    return ResponseEntity.ok().body(accessToken);
  }

  @PostMapping("/sign-in")
  public ResponseEntity<String> signin(@RequestBody @Valid SigninDTO signinDTO){
    String accessToken = authService.signin(signinDTO);

    return ResponseEntity.ok().body(accessToken);
  }
}
