package kz.alabs.shopapi.items.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.alabs.shopapi.items.dto.*;
import kz.alabs.shopapi.items.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Items")
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Add a new product to a database")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ItemEditResponse create(@RequestBody ItemCreate item){
        return itemService.create(item);
    }

    @Operation(summary = "Update a products data by id")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ItemEditResponse update(@PathVariable Long id, @RequestBody ItemUpdate item){
        return itemService.update(id, item);
    }

    @Operation(summary = "Return a list of products by search criteria")
    @GetMapping
    public Page<ItemView> findAllPageable(Pageable pageable, @RequestBody(required = false) ItemSearch search){
        return itemService.findAllPageable(pageable, search);
    }

    @Operation(summary = "Return an item data by id")
    @GetMapping("/{id}")
    public ItemView findById(@PathVariable Long id){
        return itemService.findById(id);
    }

    @Operation(summary = "Soft delete an item by id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id){
        itemService.delete(id);
    }

}
