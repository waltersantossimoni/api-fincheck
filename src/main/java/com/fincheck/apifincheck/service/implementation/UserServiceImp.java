package com.fincheck.apifincheck.service.implementation;

import com.fincheck.apifincheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {
  @Override
  public String getUserById(String userId) {
    return "Hello";
  }
}
