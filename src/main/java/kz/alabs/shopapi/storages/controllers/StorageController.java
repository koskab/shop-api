package kz.alabs.shopapi.storages.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.alabs.shopapi.storages.dto.StorageCreate;
import kz.alabs.shopapi.storages.dto.StorageDelete;
import kz.alabs.shopapi.storages.dto.StorageEditResponse;
import kz.alabs.shopapi.storages.dto.StorageUpdate;
import kz.alabs.shopapi.storages.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Storages Controller", description = "Controller for operations on products in shops storages")
@RestController
@RequiredArgsConstructor
@RequestMapping("/storages")
public class StorageController {

    private final StorageService service;

    @Operation(summary = "Add a new item to a shops storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StorageEditResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StorageEditResponse create(@RequestBody StorageCreate storageCreate){
        return service.create(storageCreate);
    }

    @Operation(summary = "Update information about an existing item in a storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StorageEditResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public StorageEditResponse update(@RequestBody StorageUpdate storageUpdate){
        return service.update(storageUpdate);
    }

    @Operation(summary = "Delete an item from a storage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@RequestBody StorageDelete storageDelete){
        service.delete(storageDelete);
    }
    
}
