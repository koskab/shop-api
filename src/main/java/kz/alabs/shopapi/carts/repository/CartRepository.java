package kz.alabs.shopapi.carts.repository;

import kz.alabs.shopapi.carts.dto.CartHistory;
import kz.alabs.shopapi.carts.dto.CartItem;
import kz.alabs.shopapi.carts.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT DISTINCT c.shop.id FROM Cart c WHERE c.user.id = :userId and c.isPaid = false")
    List<Long> getShopIdsList(Long userId);

    boolean existsByUser_IdAndIsPaidFalse(Long userId);

    @Query("""
            select new kz.alabs.shopapi.carts.dto.CartItem(item, quantity, cost)
            from Cart
            where user.id = :userId and shop.id = :shopId and isDeleted = 0 and isPaid = false 
            """)
    List<CartItem> findItemsByUserAndShop(Long userId, Long shopId);

    List<Cart> findAllByUser_IdAndIsPaidFalse(Long userId);

    @Query("""
            select new kz.alabs.shopapi.carts.dto.CartHistory(paidAt, sum(cost))
            from Cart
            where user.id = :userId and isPaid = true
            group by paidAt
            order by paidAt desc
            """)
    List<CartHistory> findPurchases(Long userId);

    boolean existsByUserIdAndShopIdAndItemIdAndIsPaidFalse(Long userId, Long shopId, Long itemId);

    Cart findByUser_IdAndShop_IdAndItem_IdAndIsPaidFalse(Long userId, Long shopId, Long itemId);

}
