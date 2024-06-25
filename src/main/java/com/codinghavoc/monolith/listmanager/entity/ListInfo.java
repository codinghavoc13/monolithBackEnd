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
    @Override
    public String toString() {
        return "ListInfo [listId=" + listId + ", userId=" + userId + ", listName=" + listName + ", listNotes="
                + listNotes + ", ordered=" + ordered + "]";
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((listId == null) ? 0 : listId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((listName == null) ? 0 : listName.hashCode());
        result = prime * result + ((listNotes == null) ? 0 : listNotes.hashCode());
        result = prime * result + ((ordered == null) ? 0 : ordered.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ListInfo other = (ListInfo) obj;
        if (listId == null) {
            if (other.listId != null)
                return false;
        } else if (!listId.equals(other.listId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (listName == null) {
            if (other.listName != null)
                return false;
        } else if (!listName.equals(other.listName))
            return false;
        if (listNotes == null) {
            if (other.listNotes != null)
                return false;
        } else if (!listNotes.equals(other.listNotes))
            return false;
        if (ordered == null) {
            if (other.ordered != null)
                return false;
        } else if (!ordered.equals(other.ordered))
            return false;
        return true;
    }

    public ListInfo(ListInfoDto dto){
        this.userId = dto.userId;
        this.listName = dto.listName;
        this.listNotes = dto.listNotes;
        this.ordered = dto.ordered;
    }
}
