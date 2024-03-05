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
public class UserCreate {

    private LocalDate birthdate;
    private String fullName;
    private String email;
    private String password;
    private String rePassword;

}
