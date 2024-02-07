package com.fincheck.apifincheck.dto;

import java.util.UUID;

public record SavedUserDTO(
  UUID id,
  String name,
  String email
) {}
