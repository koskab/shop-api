package kz.alabs.shopapi.shops.service;

import kz.alabs.shopapi.shops.dto.*;
import kz.alabs.shopapi.shops.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {

    ShopEditResponse create(ShopCreate shopCreate);

    ShopEditResponse update(Long id, ShopUpdate shopUpdate);

    Shop getEntityById(Long id);

    Page<ShopView> findAllPageable(Pageable pageable, ShopSearch search);

    ShopDetails findById(Long id);

    void delete(Long id);

}
