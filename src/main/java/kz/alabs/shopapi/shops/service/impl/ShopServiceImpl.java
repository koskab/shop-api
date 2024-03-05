package kz.alabs.shopapi.shops.service.impl;

import jakarta.persistence.criteria.Predicate;
import kz.alabs.shopapi.core.exception.BadRequestException;
import kz.alabs.shopapi.core.exception.NotFoundException;
import kz.alabs.shopapi.items.entity.Item;
import kz.alabs.shopapi.items.service.ItemService;
import kz.alabs.shopapi.shops.dto.*;
import kz.alabs.shopapi.shops.entity.Shop;
import kz.alabs.shopapi.shops.mapper.ShopMapper;
import kz.alabs.shopapi.shops.repository.ShopRepository;
import kz.alabs.shopapi.shops.service.ShopService;
import kz.alabs.shopapi.storages.entity.Storage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository repository;
    private final ItemService itemService;

    @Override
    @Transactional
    public ShopEditResponse create(ShopCreate shopCreate){
        if(repository.existsByNameAndAddress(shopCreate.getName(), shopCreate.getAddress()))
            throw new BadRequestException("Can't be more than one shop with this name in the same building");

        if(StringUtils.isBlank(shopCreate.getName())
                || StringUtils.isBlank(shopCreate.getAddress()))
            throw new BadRequestException("Name and address fields can't be empty");

        Shop shop = ShopMapper.INSTANCE.toEntity(shopCreate);
        shop = repository.save(shop);
        if(shopCreate.getItems() != null || !shopCreate.getItems().isEmpty()){
            if(!shopCreate.getItems().stream().map(ItemAdd::getCount).filter(count -> count < 0).toList().isEmpty())
                throw new BadRequestException("Items count can't be negative");

            for(ItemAdd itemAdd : shopCreate.getItems()){
                Storage storage = generateStorage(shop, itemAdd);
                shop.addStorage(storage);
            }
        }
        return new ShopEditResponse(shop.getId());
    }

    @Override
    @Transactional
    public ShopEditResponse update(Long id, ShopUpdate shopUpdate){
        if(repository.existsByNameAndAddress(shopUpdate.getName(), shopUpdate.getAddress()))
            throw new BadRequestException("Can't be more than one shop with this name in the same building");

        if(StringUtils.isBlank(shopUpdate.getName())
                || StringUtils.isBlank(shopUpdate.getAddress()))
            throw new BadRequestException("Name and address fields can't be empty");

        Shop entity = getEntityById(id);
        Shop shop = ShopMapper.INSTANCE.toEntity(entity, shopUpdate);
        shop = repository.save(shop);
        return new ShopEditResponse(shop.getId());
    }

    @Override
    @Transactional
    public Shop getEntityById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop not found"));
    }

    @Override
    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id))
            throw new NotFoundException("Shop not found");

        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShopView> findAllPageable(Pageable pageable, ShopSearch search){
        Specification<Shop> where = buildSpecification(search);
        return repository.findAll(where, pageable).map(ShopMapper.INSTANCE::toView);
    }

    @Override
    @Transactional(readOnly = true)
    public ShopDetails findById(Long id){
        Shop shop = getEntityById(id);
        return ShopMapper.INSTANCE.toDetailedView(shop);
    }

    private Storage generateStorage(Shop shop, ItemAdd itemAdd){
        Item item = itemService.getEntityById(itemAdd.getId());
        return new Storage(shop, item, itemAdd.getCount());
    }

    private Specification<Shop> buildSpecification(ShopSearch search){
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.and();

            if(search == null)
                return predicate;

            if(StringUtils.isNotBlank(search.getName()))
                predicate = criteriaBuilder.and(criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root.get("name")), "%" + search.getName().toLowerCase() + "%"));

            if(StringUtils.isNotBlank(search.getAddress()))
                predicate = criteriaBuilder.and(criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root.get("address")), "%" + search.getAddress().toLowerCase() + "%"));

            if(StringUtils.isNotBlank(search.getPhoneNumber()))
                predicate = criteriaBuilder.and(criteriaBuilder.like(root.get("phoneNumber"), search.getPhoneNumber()));

            return predicate;
        };
    }

}