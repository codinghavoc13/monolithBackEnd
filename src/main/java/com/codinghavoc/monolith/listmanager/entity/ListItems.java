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
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "list_items")
public class ListItems {
    @Id
    @Column(name = "list_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listItemId;

    @NonNull
    @Column(name = "item_name")
    private String itemName;

    @NonNull
    @Column(name = "quantity")
    private Long quantity;

    @NonNull
    @Column(name = "order_position")
    private Long orderPosition;

    @NonNull
    @Column(name = "item_notes")
    private String itemNotes;
}
