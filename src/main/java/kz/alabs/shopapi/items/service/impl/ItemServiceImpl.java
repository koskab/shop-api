package kz.alabs.shopapi.items.service.impl;

import jakarta.persistence.criteria.Predicate;
import kz.alabs.shopapi.core.exception.BadRequestException;
import kz.alabs.shopapi.core.exception.NotFoundException;
import kz.alabs.shopapi.items.dto.*;
import kz.alabs.shopapi.items.entity.Item;
import kz.alabs.shopapi.items.mapper.ItemMapper;
import kz.alabs.shopapi.items.repository.ItemRepository;
import kz.alabs.shopapi.items.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Transactional
    @Override
    public ItemEditResponse create(ItemCreate itemCreate) {
        if(repository.existsByCode(itemCreate.getCode()))
            throw new BadRequestException("Item already exists");

        Item item = ItemMapper.INSTANCE.toEntity(itemCreate);
        item = repository.save(item);
        return new ItemEditResponse(item.getId());
    }

    @Transactional
    @Override
    public ItemEditResponse update(Long id, ItemUpdate itemUpdate) {
        Item requestedItem = getEntityById(id);
        Item item = ItemMapper.INSTANCE.toEntity(requestedItem, itemUpdate);
        item = repository.save(item);
        return new ItemEditResponse(item.getId());
    }

    @Transactional(readOnly = true)
    public Item getEntityById(Long id){
        return repository.findById(id).
                orElseThrow(()-> new NotFoundException("Item not found"));
    }

    @Transactional(readOnly = true)
    public Page<ItemView> findAllPageable(Pageable pageable, ItemSearch search){
        Specification<Item> where = buildSpecification(search);
        return repository.findAll(where, pageable).map(ItemMapper.INSTANCE::toView);
    }

    @Transactional(readOnly = true)
    public ItemView findById(Long id){
        return ItemMapper.INSTANCE.toView(getEntityById(id));
    }

    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id))
            throw new NotFoundException("Item not found");

        repository.deleteById(id);
    }

    private Specification<Item> buildSpecification(ItemSearch search){
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.and();

            if(search == null)
                return predicate;

            boolean hasPriceFrom = search.getPriceFrom() != null;
            boolean hasPriceTo = search.getPriceTo() != null;

            if(StringUtils.isNotBlank(search.getName()))
                predicate = criteriaBuilder.and(criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root.get("name")), "%" + search.getName().toLowerCase() + "%"));

            if(StringUtils.isNotBlank(search.getDescription()))
                predicate = criteriaBuilder.and(criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root.get("description")), "%" + search.getDescription().toLowerCase() + "%"));

            if(StringUtils.isNotBlank(search.getCode()))
                predicate = criteriaBuilder.and(criteriaBuilder.like(root.get("code"), search.getCode()));

            if(hasPriceTo && hasPriceFrom && search.getPriceFrom().compareTo(search.getPriceTo()) > 0)
                throw new BadRequestException("Lower border of price can't be greater than upper border");

            if(hasPriceFrom)
                predicate = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), search.getPriceFrom()));

            if(hasPriceTo)
                predicate = criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("price"), search.getPriceTo()));

            return predicate;
        };
    }

}
