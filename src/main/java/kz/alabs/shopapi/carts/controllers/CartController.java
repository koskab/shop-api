package kz.alabs.shopapi.carts.controllers;

import kz.alabs.shopapi.carts.dto.*;
import kz.alabs.shopapi.carts.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public CartEditResponse create(@RequestBody CartCreate cartCreate){
        return service.create(cartCreate);
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public CartEditResponse update(@RequestBody CartUpdate cartUpdate){
        return service.update(cartUpdate);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public CartResponse view(){
        return service.view();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PutMapping("/payment")
    @PreAuthorize("isAuthenticated()")
    public void purchase(){
        service.purchase();
    }

    @GetMapping("/history")
    @PreAuthorize("isAuthenticated()")
    public List<CartHistory> history(){
        return service.viewHistory();
    }

}
