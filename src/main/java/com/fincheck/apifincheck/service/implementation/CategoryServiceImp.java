package com.fincheck.apifincheck.service.implementation;

import com.fincheck.apifincheck.dto.CategoryDTO;
import com.fincheck.apifincheck.model.Category;
import com.fincheck.apifincheck.repository.CategoryRepository;
import com.fincheck.apifincheck.service.CategoryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<CategoryDTO> findAllByUserId(UUID userId) {
    List<Category> categories = categoryRepository
      .findAllByUserId(userId)
      .orElseThrow(() -> new RuntimeException("Categories not found."));

    return categories
      .stream()
      .map(category -> modelMapper.map(category, CategoryDTO.class))
      .toList();
  }
}
