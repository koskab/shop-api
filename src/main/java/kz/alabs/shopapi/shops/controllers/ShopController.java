package kz.alabs.shopapi.shops.controllers;

import kz.alabs.shopapi.shops.dto.*;
import kz.alabs.shopapi.shops.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
public class ShopController {

    private final ShopService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public Page<ShopView> findAllPageable(Pageable pageable, @RequestBody(required = false) ShopSearch shopSearch){
        return service.findAllPageable(pageable, shopSearch);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ShopDetails findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ShopEditResponse create(@RequestBody ShopCreate shopCreate){
        return service.create(shopCreate);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ShopEditResponse update(@PathVariable Long id, @RequestBody ShopUpdate shopUpdate){
        return service.update(id, shopUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
