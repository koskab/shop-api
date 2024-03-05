package kz.alabs.shopapi.carts.mapper;

import kz.alabs.shopapi.carts.dto.*;
import kz.alabs.shopapi.carts.entity.Cart;
import kz.alabs.shopapi.items.mapper.ItemMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true), uses = ItemMapper.class)
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart toEntity(CartUpdate cartUpdate);

    Cart toEntity(CartCreate cartCreate);

    CartItem toInCart(Cart cart);

}
