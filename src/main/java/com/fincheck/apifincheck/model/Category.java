package com.fincheck.apifincheck.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_category")
public class Category {
  @Id
  @GeneratedValue
  private UUID id;
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
  private String name;
  private String icon;
  @Enumerated(EnumType.STRING)
  private TransactionType type;
  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private List<Transaction> transactions;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return Objects.equals(id, category.id) && Objects.equals(user, category.user) && Objects.equals(name, category.name) && Objects.equals(icon, category.icon) && type == category.type && Objects.equals(transactions, category.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, user, name, icon, type, transactions);
  }
}
