package kz.alabs.shopapi.storages.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StorageUpdate {

    private Long shopId;
    private Long itemId;
    private Integer count;

}
