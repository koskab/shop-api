package kz.alabs.shopapi.items.controllers;

import kz.alabs.shopapi.items.dto.*;
import kz.alabs.shopapi.items.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ItemEditResponse create(@RequestBody ItemCreate item){
        return itemService.create(item);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ItemEditResponse update(@PathVariable Long id, @RequestBody ItemUpdate item){
        return itemService.update(id, item);
    }

    @GetMapping
    public Page<ItemView> findAllPageable(Pageable pageable, @RequestBody(required = false) ItemSearch search){
        return itemService.findAllPageable(pageable, search);
    }

    @GetMapping("/{id}")
    public ItemView findById(@PathVariable Long id){
        return itemService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id){
        itemService.delete(id);
    }

}
