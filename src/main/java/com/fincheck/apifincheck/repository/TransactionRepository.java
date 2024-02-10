package com.fincheck.apifincheck.repository;

import com.fincheck.apifincheck.model.Transaction;
import com.fincheck.apifincheck.model.TransactionType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  @Query(
    "SELECT t FROM Transaction t " +
      "WHERE t.user.id = :userId " +
      "AND (:bankAccountId IS NULL OR t.bankAccount.id = :bankAccountId) " +
      "AND (:type IS NULL OR t.type = :type) " +
      "AND t.date >= :startDate AND t.date < :endDate"
  )
  Optional<List<Transaction>> findByIdWithFilters(
    UUID userId,
    UUID bankAccountId,
    TransactionType type,
    LocalDateTime startDate,
    LocalDateTime endDate
  );

  @Query(
    "SELECT COUNT(t.id) > 0 "
      + "FROM Transaction t "
      + "WHERE t.id = :transactionId "
      + "AND t.user.id = :userId"
  )
  boolean existsByIdAndUserId(UUID transactionId, UUID userId);
}
