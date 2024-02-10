package com.fincheck.apifincheck.validator;

import com.fincheck.apifincheck.repository.BankAccountRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateBankAccountOwnershipService {

  private final BankAccountRepository bankAccountRepository;

  public void validate(UUID bankAccountId, UUID userId) {
    boolean exists = bankAccountRepository.existsByIdAndUserId(bankAccountId, userId);

    if (!exists) {
      throw new RuntimeException("Bank account not found.");
    }
  }
}
