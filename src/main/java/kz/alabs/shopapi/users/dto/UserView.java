package kz.alabs.shopapi.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserView {

    private Long id;
    private String fullName;
    private String email;
    private LocalDate birthdate;
    private BigDecimal balance;

}
