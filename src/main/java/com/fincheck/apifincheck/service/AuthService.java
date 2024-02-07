package com.fincheck.apifincheck.service;

import com.fincheck.apifincheck.dto.SigninDTO;
import com.fincheck.apifincheck.dto.SignupDTO;

public interface AuthService {
  String signup(SignupDTO signUpDTO);
  String signin(SigninDTO signinDTO);
}
