package kz.alabs.shopapi.storages.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.alabs.shopapi.storages.dto.StorageCreate;
import kz.alabs.shopapi.storages.dto.StorageDelete;
import kz.alabs.shopapi.storages.dto.StorageEditResponse;
import kz.alabs.shopapi.storages.dto.StorageUpdate;
import kz.alabs.shopapi.storages.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Storages")
@RestController
@RequiredArgsConstructor
@RequestMapping("/storages")
public class StorageController {

    private final StorageService service;

    @Operation(summary = "Add a new item to a shops storage")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StorageEditResponse create(@RequestBody StorageCreate storageCreate){
        return service.create(storageCreate);
    }

    @Operation(summary = "Update information about an existing item in a storage")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StorageEditResponse update(@RequestBody StorageUpdate storageUpdate){
        return service.update(storageUpdate);
    }

    @Operation(summary = "Delete an item from a storage")
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@RequestBody StorageDelete storageDelete){
        service.delete(storageDelete);
    }
    
}
