package kz.alabs.shopapi.storages.service;

import kz.alabs.shopapi.storages.dto.StorageCreate;
import kz.alabs.shopapi.storages.dto.StorageDelete;
import kz.alabs.shopapi.storages.dto.StorageEditResponse;
import kz.alabs.shopapi.storages.dto.StorageUpdate;
import kz.alabs.shopapi.storages.entity.Storage;

public interface StorageService {

    StorageEditResponse update(StorageUpdate storageUpdate);

    StorageEditResponse create(StorageCreate storageCreate);

    void delete(StorageDelete storageDelete);

    Storage getEntityByShopAndItem(Long shopId, Long itemId);

}
