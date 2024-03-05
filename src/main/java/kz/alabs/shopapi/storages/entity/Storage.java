package kz.alabs.shopapi.storages.entity;

import jakarta.persistence.*;
import kz.alabs.shopapi.audit.AuditEntity;
import kz.alabs.shopapi.items.entity.Item;
import kz.alabs.shopapi.shops.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name = "shops_storage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE shops_storage SET is_deleted = 1, deleted_at = now() WHERE id = ?")
@Where(clause = "is_deleted = 0")
public class Storage extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "count", nullable = false)
    private Integer count = 1;

    @Transient
    public void reduce(Integer quantity){
        this.count -= quantity;
    }

}
