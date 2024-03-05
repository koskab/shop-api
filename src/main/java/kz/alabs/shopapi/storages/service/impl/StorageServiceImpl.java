package kz.alabs.shopapi.storages.service.impl;

import kz.alabs.shopapi.core.exception.BadRequestException;
import kz.alabs.shopapi.core.exception.NotFoundException;
import kz.alabs.shopapi.items.entity.Item;
import kz.alabs.shopapi.items.service.ItemService;
import kz.alabs.shopapi.shops.dto.ItemAdd;
import kz.alabs.shopapi.shops.entity.Shop;
import kz.alabs.shopapi.shops.service.ShopService;
import kz.alabs.shopapi.storages.dto.StorageCreate;
import kz.alabs.shopapi.storages.dto.StorageDelete;
import kz.alabs.shopapi.storages.dto.StorageEditResponse;
import kz.alabs.shopapi.storages.dto.StorageUpdate;
import kz.alabs.shopapi.storages.entity.Storage;
import kz.alabs.shopapi.storages.repository.StorageRepository;
import kz.alabs.shopapi.storages.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageRepository repository;
    private final ShopService shopService;
    private final ItemService itemService;

    @Override
    @Transactional
    public StorageEditResponse create(StorageCreate storageCreate){
        var itemIds = storageCreate.getItems().stream().map(ItemAdd::getId).toList();
        if (repository.existsByShopIdAndItemIdIn(storageCreate.getShopId(), itemIds))
            throw new BadRequestException("Shop already has item from the list");

        if(!storageCreate.getItems().stream().map(ItemAdd::getCount).filter(count -> count < 0).toList().isEmpty())
            throw new BadRequestException("Items count can't be negative");

        List<Storage> storages = collectStorages(storageCreate);
        repository.saveAll(storages);
        return new StorageEditResponse(storageCreate.getShopId());
    }

    @Override
    @Transactional
    public StorageEditResponse update(StorageUpdate storageUpdate){
        if(storageUpdate.getCount() < 0)
            throw new BadRequestException("Items count can't be negative");

        Long shopId = storageUpdate.getShopId();
        Long itemId = storageUpdate.getItemId();
        Storage storage = repository.findByShopIdAndItemId(shopId, itemId)
                .orElseThrow(() -> new NotFoundException("Storage not found"));

        storage.setCount(storageUpdate.getCount());
        repository.save(storage);
        return new StorageEditResponse(shopId);
    }

    @Override
    @Transactional
    public void delete(StorageDelete storageDelete){
        if (!repository.existsByShopIdAndItemId(storageDelete.getShopId(), storageDelete.getItemId()))
            throw new NotFoundException("Item not found in this shop");

        repository.deleteByShopIdAndItemId(storageDelete.getShopId(), storageDelete.getItemId());
    }

    @Override
    @Transactional(readOnly = true)
    public Storage getEntityByShopAndItem(Long shopId, Long itemId){
        return repository.findByShopIdAndItemId(shopId, itemId)
                .orElseThrow(() -> new NotFoundException("Storage not found"));
    }

    private List<Storage> collectStorages(StorageCreate storageCreate){
        List<Storage> storages = new ArrayList<>();
        for(ItemAdd itemAdd : storageCreate.getItems()) {
            Shop shop = shopService.getEntityById(storageCreate.getShopId());
            Item item = itemService.getEntityById(itemAdd.getId());
            Storage storage = new Storage(shop, item, itemAdd.getCount());
            storages.add(storage);
        }
        return storages;
    }

}
