package kz.alabs.shopapi.users.controllers;

import kz.alabs.shopapi.core.security.LogoutService;
import kz.alabs.shopapi.users.dto.*;
import kz.alabs.shopapi.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LogoutService logoutService;

    @PostMapping("/sign-in")
    public UserSignInResponse signIn(@RequestBody UserSignIn request) {
        return userService.authenticate(request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserView findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PostMapping
    public UserEditResponse create(@RequestBody UserCreate user){
        return userService.create(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserEditResponse update(@PathVariable Long id, @RequestBody UserUpdate user){
        return userService.update(id, user);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @PutMapping("/deposit")
    @PreAuthorize("isAuthenticated()")
    public UserEditResponse deposit(@RequestParam(name = "balance") BigDecimal balance){
        return userService.deposit(balance);
    }

}
