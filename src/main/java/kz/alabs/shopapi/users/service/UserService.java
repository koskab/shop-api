package kz.alabs.shopapi.users.service;

import kz.alabs.shopapi.users.dto.*;
import kz.alabs.shopapi.users.entity.User;

import java.math.BigDecimal;

public interface UserService {

    UserSignInResponse authenticate(UserSignIn request);

    User getCurrentUser();

    UserView findById(Long id);

    User getEntityById(Long id);

    User getEntityByEmail(String email);

    UserEditResponse create(UserCreate userCreate);

    void delete(Long id);

    UserEditResponse update(Long id, UserUpdate userUpdate);

    UserEditResponse deposit(BigDecimal balance);

}
