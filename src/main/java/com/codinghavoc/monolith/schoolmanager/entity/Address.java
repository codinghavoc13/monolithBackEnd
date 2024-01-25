package com.codinghavoc.monolith.schoolmanager.entity;

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
@Table(name = "address", schema = "school_manager")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private Long addressId;

    @NonNull
    @Column(name = "address_1")
    private String address1;

    @NonNull
    @Column(name = "address_2")
    private String address2;

    @NonNull
    @Column(name = "city")
    private String city;

    @NonNull
    @Column(name = "state")
    private String state;

    @NonNull
    @Column(name = "zip_code")
    private String zipCode;
    
}
