package com.fincheck.apifincheck.repository;

import com.fincheck.apifincheck.model.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

  @Query(
    "SELECT c FROM Category c "
      + "WHERE c.user.id = :userId"
  )
  Optional<List<Category>> findAllByUserId(UUID userId);

  @Query(
    "SELECT COUNT(c.id) > 0 "
      + "FROM Category c "
      + "WHERE c.id = :categoryId "
      + "AND c.user.id = :userId"
  )
  boolean existsByIdAndUserId(UUID categoryId, UUID userId);
}
