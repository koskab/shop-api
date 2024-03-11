package kz.alabs.shopapi.users.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@Tag(name = "Users Controller", description = "Controller for operations on users, such as crud, sign in etc.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LogoutService logoutService;

    @Operation(summary = "Sign in a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserSignInResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/sign-in")
    public UserSignInResponse signIn(@RequestBody UserSignIn request) {
        return userService.authenticate(request);
    }

    @Operation(summary = "Log out")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }

    @Operation(summary = "Return user info by id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserView.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserView findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @Operation(summary = "Create a new user", description = "Adds a new user to a database with ROLE_USER")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserEditResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public UserEditResponse create(@RequestBody UserCreate user){
        return userService.create(user);
    }

    @Operation(summary = "Update users properties in database by id")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserEditResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode ="500", description = "Internal server error"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")

    })
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserEditResponse update(@PathVariable Long id, @RequestBody UserUpdate user){
        return userService.update(id, user);
    }

    @Operation(summary = "Soft delete user from a database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }

    @Operation(summary = "Deposit money to users balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserEditResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PutMapping("/deposit")
    @PreAuthorize("isAuthenticated()")
    public UserEditResponse deposit(@RequestParam(name = "balance") BigDecimal balance){
        return userService.deposit(balance);
    }

}
