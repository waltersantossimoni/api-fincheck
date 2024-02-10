package com.fincheck.apifincheck.dto;

import com.fincheck.apifincheck.model.Transaction;
import com.fincheck.apifincheck.model.TransactionType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {

  private String name;
  private String icon;
  private TransactionType type;
  private List<Transaction> transactions;
}