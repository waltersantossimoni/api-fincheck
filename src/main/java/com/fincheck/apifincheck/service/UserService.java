package com.fincheck.apifincheck.service;

import com.fincheck.apifincheck.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
  Optional<User> getUserById(UUID userId);
}
