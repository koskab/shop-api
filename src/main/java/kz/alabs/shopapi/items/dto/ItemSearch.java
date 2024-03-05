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
public class ItemSearch {

    private String code;
    private String name;
    private String description;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;

}
