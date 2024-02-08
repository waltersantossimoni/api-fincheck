package com.fincheck.apifincheck.controller;

import com.fincheck.apifincheck.dto.BankAccountDTO;
import com.fincheck.apifincheck.service.BankAccountService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/bank-accounts")
@RestController
public class BankAccountController {

  private final BankAccountService bankAccountService;

  @PostMapping("/user/{userId}")
  public ResponseEntity<BankAccountDTO> create(
    @PathVariable UUID userId,
    @RequestBody @Valid BankAccountDTO bankAccountDTO
  ) {
    BankAccountDTO response = bankAccountService.create(userId, bankAccountDTO);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<BankAccountDTO>> findAllByUserId(@PathVariable UUID userId) {
    List<BankAccountDTO> bankAccounts = bankAccountService.findAllByUserId(userId);

    return ResponseEntity.ok().body(bankAccounts);
  }

  @PutMapping("/{bankAccountId}/user/{userId}")
  public ResponseEntity<Void> update(
    @PathVariable UUID bankAccountId,
    @PathVariable UUID userId,
    @RequestBody @Valid BankAccountDTO bankAccountDTO
  ) {
    bankAccountService.update(userId, bankAccountId, bankAccountDTO);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{bankAccountId}/user/{userId}")
  public ResponseEntity<Void> delete(
    @PathVariable UUID bankAccountId,
    @PathVariable UUID userId
  ) {
    bankAccountService.delete(userId, bankAccountId);

    return ResponseEntity.noContent().build();
  }
}
