package com.fincheck.apifincheck.service;

import com.fincheck.apifincheck.dto.CategoryDTO;
import java.util.List;
import java.util.UUID;

public interface CategoryService {

  List<CategoryDTO> findAllByUserId(UUID userId);
}
