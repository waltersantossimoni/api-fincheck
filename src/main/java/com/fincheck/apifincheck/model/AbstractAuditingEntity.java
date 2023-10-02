package com.fincheck.apifincheck.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity {

  @CreatedBy
//  @Hidden
  private String createdBy = "admin";
  @CreatedDate
//  @Hidden
  private Instant createdDate = Instant.now();
  @LastModifiedBy
  @JsonIgnore
//  @Hidden
  private String lastModifiedBy;
  @LastModifiedDate
  @JsonIgnore
//  @Hidden
  private Instant lastModifiedDate = Instant.now();
//  @Hidden
  private Boolean deleted = false;

  public abstract UUID getId();
}