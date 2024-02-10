package com.fincheck.apifincheck.validator;

import com.fincheck.apifincheck.repository.TransactionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateTransactionOwnershipService {

  private final TransactionRepository transactionRepository;

  public void validate(UUID transactionId, UUID userId) {
    boolean exists = transactionRepository.existsByIdAndUserId(transactionId, userId);

    if (!exists) {
      throw new RuntimeException("Transaction not found.");
    }
  }
}
