package kz.alabs.shopapi.items.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreate {

    private String code;
    private String name;
    private BigDecimal price = BigDecimal.ZERO;

}
