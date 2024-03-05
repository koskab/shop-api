package kz.alabs.shopapi.items.service;

import kz.alabs.shopapi.items.dto.*;
import kz.alabs.shopapi.items.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ItemService {

    ItemEditResponse create(ItemCreate itemCreate);

    ItemEditResponse update(Long id, ItemUpdate itemUpdate);

    Page<ItemView> findAllPageable(Pageable pageable, ItemSearch search);

    ItemView findById(Long id);

    Item getEntityById(Long id);

    void delete(Long id);

}
