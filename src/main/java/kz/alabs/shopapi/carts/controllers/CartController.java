package kz.alabs.shopapi.carts.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.alabs.shopapi.carts.dto.*;
import kz.alabs.shopapi.carts.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Carts")
@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @Operation(summary = "Add a new cart to a database")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public CartEditResponse create(@RequestBody CartCreate cartCreate){
        return service.create(cartCreate);
    }

    @Operation(summary = "Update cart contents properties")
    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public CartEditResponse update(@RequestBody CartUpdate cartUpdate){
        return service.update(cartUpdate);
    }

    @Operation(summary = "Return a current users cart info")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public CartResponse view(){
        return service.view();
    }

    @Operation(summary = "Delete an item from a cart")
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @Operation(summary = "Purchase all of the items in a cart")
    @PutMapping("/payment")
    @PreAuthorize("isAuthenticated()")
    public void purchase(){
        service.purchase();
    }

    @Operation(summary = "Return history of current users purchases")
    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    public List<CartHistory> history(){
        return service.viewHistory();
    }

}
