package com.codinghavoc.monolith.listmanager.entity;

import com.codinghavoc.monolith.listmanager.dto.ListInfoDto;

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
@Table(name="list_info", schema = "list_manager")
public class ListInfo {
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

    @NonNull
    @Column(name = "list_notes")
    private String listNotes;

    @NonNull
    @Column(name = "ordered")
    private Boolean ordered;

    public ListInfo(ListInfoDto dto){
        this.userId = dto.userId;
        this.listName = dto.listName;
        this.listNotes = dto.listNotes;
        this.ordered = dto.ordered;
    }
}
