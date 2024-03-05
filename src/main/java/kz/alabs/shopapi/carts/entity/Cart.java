package kz.alabs.shopapi.carts.entity;

import kz.alabs.shopapi.audit.AuditEntity;
import kz.alabs.shopapi.items.entity.Item;
import kz.alabs.shopapi.shops.entity.Shop;
import kz.alabs.shopapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE carts SET is_deleted = 1, deleted_at = now() WHERE id = ?")
@Where(clause = "is_deleted = 0")
public class Cart extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid = false;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    public Cart(User user, Shop shop, Item item, Integer quantity, BigDecimal cost){
        this.setUser(user);
        this.setShop(shop);
        this.setItem(item);
        this.setQuantity(quantity);
        this.setCost(cost);
    }

    @Transient
    public void setPurchased(){
        this.setPaid(true);
        this.setPaidAt(LocalDateTime.now());
    }

}
