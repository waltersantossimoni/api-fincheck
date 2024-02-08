package com.fincheck.apifincheck.service;

import com.fincheck.apifincheck.dto.BankAccountDTO;
import java.util.List;
import java.util.UUID;

public interface BankAccountService {
  BankAccountDTO create(UUID userId, BankAccountDTO bankAccountDTO);

  List<BankAccountDTO> findAllByUserId(UUID userId);

  void update(UUID userId, UUID bankAccountId, BankAccountDTO bankAccountDTO);

  void delete(UUID userId, UUID bankAccountId);
}
