package com.fincheck.apifincheck.service;

import com.fincheck.apifincheck.dto.TransactionDTO;
import com.fincheck.apifincheck.model.TransactionType;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

  TransactionDTO create(UUID userId, TransactionDTO transactionDTO);

  List<TransactionDTO> findAllByUserId(
    UUID userId,
    UUID bankAccountId,
    Integer month,
    Integer year,
    TransactionType type
  );

  void update(UUID userId, UUID transactionId, TransactionDTO transactionDTO);

  void delete(UUID userId, UUID transactionId);
}
