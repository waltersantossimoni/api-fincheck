package com.fincheck.apifincheck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_bank_account")
public class BankAccount extends AbstractAuditingEntity {
  @Id
  @GeneratedValue
  private UUID id;
  private String name;
  @Column(name = "initial_balance")
  private BigDecimal initialBalance;
  private String color;
  @Enumerated(EnumType.STRING)
  private BankAccountType type;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
  @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
  private List<Transaction> transactions;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BankAccount that = (BankAccount) o;
    return
      Objects.equals(id, that.id) &&
        Objects.equals(user, that.user) &&
        Objects.equals(name, that.name) &&
        Objects.equals(initialBalance, that.initialBalance) &&
        Objects.equals(color, that.color) &&
        type == that.type &&
        Objects.equals(transactions, that.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      id,
      user,
      name,
      initialBalance,
      color,
      type,
      transactions
    );
  }
}
