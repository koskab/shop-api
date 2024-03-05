package kz.alabs.shopapi.storages.mapper;

import kz.alabs.shopapi.items.mapper.ItemMapper;
import kz.alabs.shopapi.storages.dto.StorageCreate;
import kz.alabs.shopapi.storages.dto.StorageView;
import kz.alabs.shopapi.storages.entity.Storage;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true), uses = ItemMapper.class)
public interface StorageMapper {

    StorageMapper INSTANCE = Mappers.getMapper(StorageMapper.class);

    Storage toEntity(StorageCreate storageCreate);

    Storage toEntity(StorageView storageView);

    StorageView toView(Storage storage);

}
