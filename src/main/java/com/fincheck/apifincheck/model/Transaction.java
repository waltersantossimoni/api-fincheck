package com.fincheck.apifincheck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_transaction")
public class Transaction {
  @Id
  @GeneratedValue
  private UUID id;
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
  @ManyToOne(fetch = FetchType.LAZY)
  private BankAccount bankAccount;
  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;
  private String name;
  private BigDecimal value;
  private LocalDateTime date;
  @Enumerated(EnumType.STRING)
  private TransactionType type;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(bankAccount, that.bankAccount) && Objects.equals(category, that.category) && Objects.equals(name, that.name) && Objects.equals(value, that.value) && Objects.equals(date, that.date) && type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, bankAccount, category, name, value, date, type);
  }
}
