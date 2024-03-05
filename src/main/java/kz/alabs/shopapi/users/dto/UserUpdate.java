package kz.alabs.shopapi.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {

    private String fullName;
    private String password;
    private String rePassword;
    private LocalDate birthdate;

}
