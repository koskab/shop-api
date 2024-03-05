package kz.alabs.shopapi.carts.dto;

import kz.alabs.shopapi.items.dto.ItemView;
import kz.alabs.shopapi.items.entity.Item;
import kz.alabs.shopapi.items.mapper.ItemMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    private ItemView item;
    private Integer quantity;
    private BigDecimal cost;

    public CartItem(Item item, Integer quantity, BigDecimal cost) {
        this.item = ItemMapper.INSTANCE.toView(item);
        this.quantity = quantity;
        this.cost = cost;
    }

}
