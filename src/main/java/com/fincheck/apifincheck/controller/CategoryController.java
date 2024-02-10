package com.fincheck.apifincheck.controller;

import com.fincheck.apifincheck.dto.CategoryDTO;
import com.fincheck.apifincheck.service.CategoryService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/categories")
@RestController
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<CategoryDTO>> findAllByUserId(@PathVariable UUID userId) {
    List<CategoryDTO> categories = categoryService.findAllByUserId(userId);

    return ResponseEntity.ok().body(categories);
  }
}
