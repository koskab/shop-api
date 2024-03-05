package kz.alabs.shopapi.shops.mapper;

import kz.alabs.shopapi.shops.dto.*;
import kz.alabs.shopapi.shops.entity.Shop;
import kz.alabs.shopapi.storages.dto.StorageView;
import kz.alabs.shopapi.storages.entity.Storage;
import kz.alabs.shopapi.storages.mapper.StorageMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(builder = @Builder(disableBuilder = true), uses = StorageMapper.class)
public interface ShopMapper {

    ShopMapper INSTANCE = Mappers.getMapper(ShopMapper.class);

    Shop toEntity(ShopCreate shopCreate);

    Shop toEntity(ShopSearch shopSearch);

    ShopView toView(Shop shop);

    ShopDetails toDetailedView(Shop shop);

    Shop toEntity(@MappingTarget Shop shop, ShopUpdate shopUpdate);

    Set<StorageView> toViewSet(Set<Storage> entitySet);

}
