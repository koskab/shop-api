package kz.alabs.shopapi.shops.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopView {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;

}
