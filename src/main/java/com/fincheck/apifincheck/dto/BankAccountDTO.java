package com.fincheck.apifincheck.dto;

import com.fincheck.apifincheck.model.BankAccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankAccountDTO {
  @NotBlank(message = "Name is required")
  private String name;

  @NotNull(message = "An initial balance is required.")
  private BigDecimal initialBalance;

  @NotNull(message = "A bank account type is required.")
  private BankAccountType type;

  @NotBlank(message = "Color is required.")
  private String color;
}
