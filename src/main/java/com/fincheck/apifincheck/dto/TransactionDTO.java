package com.fincheck.apifincheck.dto;

import com.fincheck.apifincheck.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDTO {

  @NotNull(message = "A valid bank account id must not be null.")
  private UUID bankAccountId;

  @NotNull(message = "A valid category id must not be null.")
  private UUID categoryId;

  @NotBlank(message = "Name is required")
  private String name;

  @NotNull(message = "A value must be provided.")
  private BigDecimal value;

  @NotNull(message = "A valid date must be provided.")
  private LocalDateTime date;

  @NotNull(message = "A transaction type is required.")
  private TransactionType type;
}
