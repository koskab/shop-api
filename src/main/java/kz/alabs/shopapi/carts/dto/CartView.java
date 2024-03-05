package kz.alabs.shopapi.carts.dto;

import kz.alabs.shopapi.shops.dto.ShopView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartView {

    private ShopView shop;
    private List<CartItem> items;

}
