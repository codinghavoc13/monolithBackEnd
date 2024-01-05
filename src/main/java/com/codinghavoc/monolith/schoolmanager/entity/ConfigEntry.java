package com.codinghavoc.monolith.schoolmanager.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="config", schema="school_manager")
public class ConfigEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="config_id")
    public Long id;

    @NonNull
    @Column(name="key")
    public String key;

    @NonNull
    @Column(name="value")
    public String value;
}
