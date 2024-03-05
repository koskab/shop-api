package kz.alabs.shopapi.shops.repository;

import kz.alabs.shopapi.shops.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    boolean existsByNameAndAddress(String name, String address);

    Page<Shop> findAll(Specification<Shop> specification, Pageable pageable);


}
