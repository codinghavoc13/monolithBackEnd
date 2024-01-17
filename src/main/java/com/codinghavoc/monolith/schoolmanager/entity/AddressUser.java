package com.codinghavoc.monolith.schoolmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address_user", schema = "school_manager", uniqueConstraints = {@UniqueConstraint(columnNames = {"address_id","user_id"})})
public class AddressUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="au_id")
    private Long au_id;
    
    @NonNull
    @Column(name = "address_id")
    private Long address_id;

    @NonNull
    @Column(name = "user_id")
    private Long user_id;
}
