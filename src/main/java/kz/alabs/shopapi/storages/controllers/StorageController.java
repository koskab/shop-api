package kz.alabs.shopapi.storages.controllers;

import kz.alabs.shopapi.storages.dto.StorageCreate;
import kz.alabs.shopapi.storages.dto.StorageDelete;
import kz.alabs.shopapi.storages.dto.StorageEditResponse;
import kz.alabs.shopapi.storages.dto.StorageUpdate;
import kz.alabs.shopapi.storages.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/storages")
public class StorageController {

    private final StorageService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StorageEditResponse create(@RequestBody StorageCreate storageCreate){
        return service.create(storageCreate);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StorageEditResponse update(@RequestBody StorageUpdate storageUpdate){
        return service.update(storageUpdate);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@RequestBody StorageDelete storageDelete){
        service.delete(storageDelete);
    }
    
}
