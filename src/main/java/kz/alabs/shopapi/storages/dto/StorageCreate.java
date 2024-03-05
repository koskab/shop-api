package kz.alabs.shopapi.storages.dto;

import kz.alabs.shopapi.shops.dto.ItemAdd;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StorageCreate {

    private Long shopId;
    private Set<ItemAdd> items;

}
