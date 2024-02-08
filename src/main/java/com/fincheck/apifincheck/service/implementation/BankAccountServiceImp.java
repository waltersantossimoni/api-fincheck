package com.fincheck.apifincheck.service.implementation;

import com.fincheck.apifincheck.dto.BankAccountDTO;
import com.fincheck.apifincheck.model.BankAccount;
import com.fincheck.apifincheck.model.TransactionType;
import com.fincheck.apifincheck.model.User;
import com.fincheck.apifincheck.repository.BankAccountRepository;
import com.fincheck.apifincheck.repository.UserRepository;
import com.fincheck.apifincheck.service.BankAccountService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImp implements BankAccountService {
  private final BankAccountRepository bankAccountRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Override
  public BankAccountDTO create(UUID userId, BankAccountDTO bankAccountDTO) {
    User user =
      userRepository
        .findById(userId)
        .orElseThrow(() -> new RuntimeException("No such user."));

    BankAccount bankAccount = modelMapper.map(bankAccountDTO, BankAccount.class);
    bankAccount.setUser(user);

    BankAccount saved = bankAccountRepository.save(bankAccount);

    return modelMapper.map(saved, BankAccountDTO.class);
  }

  @Override
  public List<BankAccountDTO> findAllByUserId(UUID userId) {
    List<BankAccount> bankAccounts = bankAccountRepository.findAllByUserId(userId);

    return bankAccounts
      .stream()
      .map(bankAccount -> {
        BigDecimal currentBalance = bankAccount
          .getTransactions()
          .stream()
          .map(transaction -> transaction.getType() == TransactionType.INCOME
            ? transaction.getValue()
            : transaction.getValue().negate()
          )
          .reduce(bankAccount.getInitialBalance(), BigDecimal::add);

        BankAccountDTO bankAccountDTO = modelMapper.map(bankAccount, BankAccountDTO.class);

        bankAccountDTO.setInitialBalance(currentBalance);

        return bankAccountDTO;
      })
      .toList();
  }

  @Override
  public void update(UUID userId, UUID bankAccountId, BankAccountDTO bankAccountDTO) {
    BankAccount bankAccount = bankAccountRepository
      .findByIdAndUserId(bankAccountId, userId)
      .orElseThrow(() -> new RuntimeException("Bank account not found"));

    modelMapper.map(bankAccountDTO, bankAccount);

    bankAccountRepository.save(bankAccount);
  }

  @Override
  public void delete(UUID userId, UUID bankAccountId) {
    BankAccount bankAccount = bankAccountRepository
      .findByIdAndUserId(bankAccountId, userId)
      .orElseThrow(() -> new RuntimeException("Bank account not found"));

    bankAccountRepository.deleteById(bankAccount.getId());
  }
}
