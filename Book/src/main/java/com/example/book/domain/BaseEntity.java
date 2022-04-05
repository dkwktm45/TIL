package com.example.book.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity implements Auditable{
    @CreatedDate
    @Column(columnDefinition = "datetime(6) default now(6)",nullable = false,updatable = false,name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "datetime(6) default now(6)",nullable = false,updatable = false,name = "updated_at")
    private LocalDateTime updatedAt;
}
