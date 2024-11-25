package com.rodasik.springex.dal.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Auditable;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class BaseEntity implements Serializable, Auditable<String, UUID, ZonedDateTime> {
    @Id
    @EqualsAndHashCode.Exclude
    private UUID id;
    private String createdBy;
    private ZonedDateTime createdDate;
    private String lastModifiedBy;
    private ZonedDateTime lastModifiedDate;

    public boolean isNew() { return this.createdDate == null; }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    @NonNull
    public Optional<String> getCreatedBy() { return Optional.ofNullable(createdBy); }

    @Override
    @NonNull
    public Optional<String> getLastModifiedBy() { return Optional.ofNullable(lastModifiedBy); }

    @Override
    @NonNull
    public Optional<ZonedDateTime> getLastModifiedDate() { return Optional.ofNullable(lastModifiedDate); }

    @Override
    @NonNull
    public Optional<ZonedDateTime> getCreatedDate() { return Optional.ofNullable(createdDate); }

    @Override
    public void setCreatedBy(@NonNull String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public void setCreatedDate(@NonNull ZonedDateTime creationDate) {
        this.createdDate = creationDate;
    }

    @Override
    public void setLastModifiedBy(@NonNull String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public void setLastModifiedDate(@NonNull ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
