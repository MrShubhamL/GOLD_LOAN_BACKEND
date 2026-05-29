package com.goldback.controllers;

import com.goldback.modals.dtos.request.OutwardRequest;
import com.goldback.services.OutwardService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/outward")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OutwardController {

    private final OutwardService outwardService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outward created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Inward or Business not found")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createOutward(@RequestBody OutwardRequest request) {
        return ResponseEntity.ok(outwardService.createOutward(request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outward updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Outward, Inward or Business not found")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateOutward(@RequestBody OutwardRequest request) {
        return ResponseEntity.ok(outwardService.updateOutward(request));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outward deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Outward not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOutward(@RequestParam UUID id) {
        return ResponseEntity.ok(outwardService.deleteOutward(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outward retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Outward not found")
    })
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getOutwardById(@RequestParam UUID id) {
        return ResponseEntity.ok(outwardService.getOutwardById(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outwards retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No Outwards found for the given Business ID")
    })
    @GetMapping("/get-by-business-id")
    public ResponseEntity<?> getOutwardsByBusinessId(@RequestParam UUID businessId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(outwardService.getOutwardsByBusiness(businessId, page, size));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outwards retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No Outwards found for the given Inward ID")
    })
    @GetMapping("/get-by-inward-id")
    public ResponseEntity<?> getOutwardsByInwardId(@RequestParam UUID inwardId) {
        return ResponseEntity.ok(outwardService.getOutwardsByInward(inwardId));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outwards retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No Outwards found")
    })
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllOutwards(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(outwardService.getOutwards(page, size));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outward collected successfully"),
            @ApiResponse(responseCode = "404", description = "Outward not found")
    })
    @PostMapping("/collect")
    public ResponseEntity<?> collectOutward(@RequestParam UUID id) {
        return ResponseEntity.ok(outwardService.collectOutward(id));
    }

}
