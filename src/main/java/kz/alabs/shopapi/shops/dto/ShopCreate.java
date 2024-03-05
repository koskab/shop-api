package kz.alabs.shopapi.shops.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopCreate {

    private String name;
    private String address;
    private String phoneNumber;
    private Set<ItemAdd> items;

}
