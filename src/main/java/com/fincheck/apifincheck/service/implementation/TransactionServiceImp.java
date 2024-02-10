package com.fincheck.apifincheck.service.implementation;

import com.fincheck.apifincheck.dto.TransactionDTO;
import com.fincheck.apifincheck.model.BankAccount;
import com.fincheck.apifincheck.model.Category;
import com.fincheck.apifincheck.model.Transaction;
import com.fincheck.apifincheck.model.TransactionType;
import com.fincheck.apifincheck.model.User;
import com.fincheck.apifincheck.repository.BankAccountRepository;
import com.fincheck.apifincheck.repository.CategoryRepository;
import com.fincheck.apifincheck.repository.TransactionRepository;
import com.fincheck.apifincheck.repository.UserRepository;
import com.fincheck.apifincheck.service.TransactionService;
import com.fincheck.apifincheck.validator.ValidateBankAccountOwnershipService;
import com.fincheck.apifincheck.validator.ValidateCategoryOwnershipService;
import com.fincheck.apifincheck.validator.ValidateTransactionOwnershipService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImp implements TransactionService {

  private final UserRepository userRepository;
  private final TransactionRepository transactionRepository;
  private final BankAccountRepository bankAccountRepository;
  private final CategoryRepository categoryRepository;
  private final ValidateTransactionOwnershipService validateTransactionOwnershipService;
  private final ValidateBankAccountOwnershipService validateBankAccountOwnershipService;
  private final ValidateCategoryOwnershipService validateCategoryOwnershipService;
  private final ModelMapper modelMapper;

  @Override
  public TransactionDTO create(UUID userId, TransactionDTO transactionDTO) {
    validateEntitiesOwnership(
      userId,
      transactionDTO.getBankAccountId(),
      transactionDTO.getCategoryId(),
      null
    );

    User user = userRepository
      .findById(userId)
      .orElseThrow(() -> new RuntimeException("No such user."));

    BankAccount bankAccount = bankAccountRepository
      .findById(transactionDTO.getBankAccountId())
      .orElseThrow(() -> new RuntimeException("No such bank account."));

    Category category = categoryRepository
      .findById(transactionDTO.getCategoryId())
      .orElseThrow(() -> new RuntimeException("No such category."));

    Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
    transaction.setUser(user);
    transaction.setBankAccount(bankAccount);
    transaction.setCategory(category);

    Transaction saved = transactionRepository.save(transaction);

    TransactionDTO response = modelMapper.map(saved, TransactionDTO.class);
    response.setBankAccountId(saved.getBankAccount().getId());
    response.setCategoryId(saved.getCategory().getId());

    return response;
  }

  @Override
  public List<TransactionDTO> findAllByUserId(
    UUID userId,
    UUID bankAccountId,
    Integer month,
    Integer year,
    TransactionType type
  ) {
    LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0);
    LocalDateTime endDate = startDate.plusMonths(1);

    List<Transaction> transactions = transactionRepository
      .findByIdWithFilters(userId, bankAccountId, type, startDate, endDate)
      .orElseThrow(() -> new RuntimeException("No such transaction."));

    return transactions
      .stream()
      .map(transaction -> {
        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
        transactionDTO.setBankAccountId(transaction.getBankAccount().getId());
        transactionDTO.setCategoryId(transaction.getCategory().getId());

        return transactionDTO;
      })
      .toList();
  }

  @Override
  public void update(UUID userId, UUID transactionId, TransactionDTO transactionDTO) {
    validateEntitiesOwnership(
      userId,
      transactionDTO.getBankAccountId(),
      transactionDTO.getCategoryId(),
      transactionId
    );

    Transaction transaction = transactionRepository
      .findById(transactionId)
      .orElseThrow(() -> new RuntimeException("Transaction not found"));

    User user = userRepository
      .findById(userId)
      .orElseThrow(() -> new RuntimeException("No such user."));

    BankAccount bankAccount = bankAccountRepository
      .findById(transactionDTO.getBankAccountId())
      .orElseThrow(() -> new RuntimeException("No such bank account."));

    Category category = categoryRepository
      .findById(transactionDTO.getCategoryId())
      .orElseThrow(() -> new RuntimeException("No such category."));

    modelMapper.map(transactionDTO, transaction);
    transaction.setUser(user);
    transaction.setBankAccount(bankAccount);
    transaction.setCategory(category);

    transactionRepository.save(transaction);
  }

  @Override
  public void delete(UUID userId, UUID transactionId) {
    validateEntitiesOwnership(
      userId,
      null,
      null,
      transactionId
    );

    Transaction transaction = transactionRepository
      .findById(transactionId)
      .orElseThrow(() -> new RuntimeException("Transaction not found"));

    transactionRepository.deleteById(transaction.getId());
  }

  private void validateEntitiesOwnership(
    UUID userId,
    UUID bankAccountId,
    UUID categoryId,
    UUID transactionId
  ) {
    if (transactionId != null) {
      validateTransactionOwnershipService.validate(transactionId, userId);
    }

    if (bankAccountId != null) {
      validateBankAccountOwnershipService.validate(bankAccountId, userId);
    }

    if (categoryId != null) {
      validateCategoryOwnershipService.validate(categoryId, userId);
    }
  }
}
