package com.fincheck.apifincheck.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface AuthenticatedUser extends UserDetails {

  UUID getId();
}
