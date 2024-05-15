package com.codinghavoc.monolith.listmanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="list_details", schema = "list_manager")
public class ListDetails {
    @Id
    @Column(name = "list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listId;

    @NonNull
    @Column(name = "user_id")
    private Long userId;

    @NonNull
    @Column(name = "list_name")
    private String listName;

    @Column(name = "list_details")
    private String listDetails;
}
