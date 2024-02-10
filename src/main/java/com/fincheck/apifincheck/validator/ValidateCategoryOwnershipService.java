package com.fincheck.apifincheck.validator;

import com.fincheck.apifincheck.repository.CategoryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateCategoryOwnershipService {

  private final CategoryRepository categoryRepository;

  public void validate(UUID categoryId, UUID userId) {
    boolean exists = categoryRepository.existsByIdAndUserId(categoryId, userId);

    if (!exists) {
      throw new RuntimeException("Category not found.");
    }
  }
}
