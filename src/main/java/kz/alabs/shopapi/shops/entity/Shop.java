package kz.alabs.shopapi.shops.entity;

import kz.alabs.shopapi.audit.AuditEntity;
import jakarta.persistence.*;
import kz.alabs.shopapi.storages.entity.Storage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE shops SET is_deleted = 1, deleted_at = now() WHERE id = ?")
@Where(clause = "is_deleted = 0")
public class Shop extends AuditEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(
            mappedBy = "shop",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Storage> storages = new HashSet<>();

    @Transient
    public void addStorage(Storage storage){
        storage.setShop(this);
        this.storages.add(storage);
    }

}
