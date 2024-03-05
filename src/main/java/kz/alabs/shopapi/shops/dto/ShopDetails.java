package kz.alabs.shopapi.shops.dto;

import kz.alabs.shopapi.storages.dto.StorageView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDetails {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private Set<StorageView> storages;

}
