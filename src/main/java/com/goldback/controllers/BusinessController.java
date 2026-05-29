package com.goldback.controllers;

import com.goldback.modals.dtos.response.BusinessResponse;
import com.goldback.services.BusinessService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BusinessController {

    private final BusinessService businessService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Businesses retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllBusinesses(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(businessService.getAllBusinesses(page, size));
    }

    @PutMapping("/update-business")
    public ResponseEntity<?> updateBusiness(@RequestBody BusinessResponse businessResponse) {
        return ResponseEntity.ok(businessService.updateBusiness(businessResponse));
    }

    @DeleteMapping("/delete-business")
    public ResponseEntity<?> deleteBusiness(@RequestParam UUID id) {
        return ResponseEntity.ok(businessService.deleteBusiness(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Business retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Business not found")
    })
    @GetMapping("/get-by-contact")
    public ResponseEntity<?> getBusinessByContact(@RequestParam String contact) {
        return ResponseEntity.ok(businessService.getBusinessByContact(contact));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Business retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Business not found")
    })
    @GetMapping("/search")
    public ResponseEntity<?> searchBusinesses(@RequestParam String keyword) {
        return ResponseEntity.ok(businessService.search(keyword));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    })
    @GetMapping("/count")
    public ResponseEntity<?> countOfBusinesses() {
        return ResponseEntity.ok(businessService.countOfBusinesses());
    }
}
