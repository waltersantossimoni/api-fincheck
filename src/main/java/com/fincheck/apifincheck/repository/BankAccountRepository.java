package com.fincheck.apifincheck.repository;

import com.fincheck.apifincheck.model.BankAccount;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

  @Query(
    "SELECT b FROM BankAccount b "
      + "WHERE b.id = :bankAccountId "
      + "AND b.user.id = :userId"
  )
  Optional<BankAccount> findByIdAndUserId(UUID bankAccountId, UUID userId);

  @Query(
    "SELECT b FROM BankAccount b "
      + "WHERE b.user.id = :userId"
  )
  Optional<List<BankAccount>> findAllByUserId(UUID userId);

  @Query(
    "SELECT COUNT(b.id) > 0 "
      + "FROM BankAccount b "
      + "WHERE b.id = :bankAccountId "
      + "AND b.user.id = :userId"
  )
  boolean existsByIdAndUserId(UUID bankAccountId, UUID userId);
}
