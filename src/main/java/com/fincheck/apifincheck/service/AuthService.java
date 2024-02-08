package com.fincheck.apifincheck.service;

import com.fincheck.apifincheck.dto.SigninDTO;
import com.fincheck.apifincheck.dto.SignupDTO;
import com.fincheck.apifincheck.dto.TokenResponseDTO;

public interface AuthService {
  TokenResponseDTO signup(SignupDTO signUpDTO);
  TokenResponseDTO signin(SigninDTO signinDTO);
}
