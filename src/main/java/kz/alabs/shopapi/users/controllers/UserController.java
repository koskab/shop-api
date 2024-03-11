package kz.alabs.shopapi.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LogoutService logoutService;

    @Operation(summary = "Sign in a user and return a JWT token")
    @PostMapping("/sign-in")
    public UserSignInResponse signIn(@RequestBody UserSignIn request) {
        return userService.authenticate(request);
    }

    @Operation(summary = "Log out")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }

    @Operation(summary = "Return user info by id")
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserView findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @Operation(summary = "Create a new user", description = "Adds a new user to a database with ROLE_USER")
    @PostMapping
    public UserEditResponse create(@RequestBody UserCreate user){
        return userService.create(user);
    }

    @Operation(summary = "Update users properties in database by id")
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserEditResponse update(@PathVariable Long id, @RequestBody UserUpdate user){
        return userService.update(id, user);
    }

    @Operation(summary = "Soft delete user from a database")
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @Operation(summary = "Deposit money to users balance")
    @PutMapping("/deposit")
    @PreAuthorize("isAuthenticated()")
    public UserEditResponse deposit(@RequestParam(name = "balance") BigDecimal balance){
        return userService.deposit(balance);
    }

}
