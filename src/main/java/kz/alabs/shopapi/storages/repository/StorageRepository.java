package kz.alabs.shopapi.storages.repository;

import kz.alabs.shopapi.storages.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long> {

    Optional<Storage> findByShopIdAndItemId(Long shopId, Long itemId);

    boolean existsByShopIdAndItemId(Long shopId, Long itemId);

    boolean existsByShopIdAndItemIdIn(Long shopId, List<Long> itemIds);

    void deleteByShopIdAndItemId(Long shopId, Long itemId);

}
