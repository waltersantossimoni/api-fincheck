package com.fincheck.apifincheck.service;

import com.fincheck.apifincheck.dto.UserDTO;
import java.util.UUID;

public interface UserService {

  UserDTO findUserById(UUID userId);
}
