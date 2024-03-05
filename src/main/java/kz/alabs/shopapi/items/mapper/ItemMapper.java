package kz.alabs.shopapi.items.mapper;

import kz.alabs.shopapi.items.dto.ItemCreate;
import kz.alabs.shopapi.items.dto.ItemUpdate;
import kz.alabs.shopapi.items.dto.ItemView;
import kz.alabs.shopapi.items.entity.Item;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item toEntity(ItemCreate itemCreate);

    Item toEntity(@MappingTarget Item item, ItemUpdate itemUpdate);

    ItemView toView(Item item);

}
