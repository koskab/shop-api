package kz.alabs.shopapi.storages.dto;

import kz.alabs.shopapi.items.dto.ItemView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StorageView {

    private ItemView item;
    private Integer count;

}
