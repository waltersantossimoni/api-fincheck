package com.fincheck.apifincheck.controller;

import com.fincheck.apifincheck.dto.TransactionDTO;
import com.fincheck.apifincheck.model.TransactionType;
import com.fincheck.apifincheck.service.TransactionService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/transactions")
@RestController
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping("/user/{userId}")
  public ResponseEntity<TransactionDTO> create(
    @PathVariable UUID userId,
    @RequestBody TransactionDTO transactionDTO
  ) {
    TransactionDTO response = transactionService.create(userId, transactionDTO);

    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{bankAccountId}/user/{userId}")
  public ResponseEntity<List<TransactionDTO>> findAllByUserId(
    @PathVariable UUID userId,
    @PathVariable UUID bankAccountId,
    @RequestParam Integer month,
    @RequestParam Integer year,
    @RequestParam TransactionType type
  ) {
    List<TransactionDTO> transactions = transactionService
      .findAllByUserId(userId, bankAccountId, month, year, type);

    return ResponseEntity.ok().body(transactions);
  }

  @PutMapping("/{transactionId}/user/{userId}")
  public ResponseEntity<Void> update(
    @PathVariable UUID userId,
    @PathVariable UUID transactionId,
    @RequestBody TransactionDTO transactionDTO
  ) {
    transactionService.update(userId, transactionId, transactionDTO);

    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{transactionId}/user/{userId}")
  public ResponseEntity<Void> delete(
    @PathVariable UUID userId,
    @PathVariable UUID transactionId
  ) {
    transactionService.delete(userId, transactionId);

    return ResponseEntity.noContent().build();
  }
}
