package com.codinghavoc.monolith.listmanager.entity;

import com.codinghavoc.monolith.listmanager.dto.ListItemDto;

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
@Table(name = "list_items", schema = "list_manager")
public class ListItem {
    @Id
    @Column(name = "list_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listItemId;

    @NonNull
    @Column(name = "list_id")
    private Long listId;

    @NonNull
    @Column(name = "item_name")
    private String itemName;

    // @NonNull
    // @Column(name = "quantity")
    // private Long quantity;

    @NonNull
    @Column(name = "order_position")
    private Long orderPosition;

    @NonNull
    @Column(name = "item_notes")
    private String itemNotes;

    public ListItem(ListItemDto dto){
        this.listId = dto.listId;
        this.itemName = dto.itemName;
        // this.quantity = dto.quantity;
        this.orderPosition = dto.orderPosition;
        this.itemNotes = dto.itemNotes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
        result = prime * result + ((orderPosition == null) ? 0 : orderPosition.hashCode());
        result = prime * result + ((itemNotes == null) ? 0 : itemNotes.hashCode());
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
        ListItem other = (ListItem) obj;
        if (itemName == null) {
            if (other.itemName != null)
                return false;
        } else if (!itemName.equals(other.itemName))
            return false;
        if (orderPosition == null) {
            if (other.orderPosition != null)
                return false;
        } else if (!orderPosition.equals(other.orderPosition))
            return false;
        if (itemNotes == null) {
            if (other.itemNotes != null)
                return false;
        } else if (!itemNotes.equals(other.itemNotes))
            return false;
        return true;
    }
}
