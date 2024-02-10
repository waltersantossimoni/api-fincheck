package com.fincheck.apifincheck.service.implementation;

import com.fincheck.apifincheck.dto.UserDTO;
import com.fincheck.apifincheck.model.User;
import com.fincheck.apifincheck.repository.UserRepository;
import com.fincheck.apifincheck.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Override
  public UserDTO findUserById(UUID userId) {
    User user = userRepository
      .findById(userId)
      .orElseThrow(() -> new RuntimeException("No such user"));

    return modelMapper.map(user, UserDTO.class);
  }
}
