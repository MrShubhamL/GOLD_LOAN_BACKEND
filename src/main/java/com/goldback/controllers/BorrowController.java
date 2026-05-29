package com.goldback.controllers;

import com.goldback.modals.dtos.request.BorrowRequest;
import com.goldback.services.BorrowService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BorrowController {

    private final BorrowService borrowService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createBorrow(@RequestBody BorrowRequest request) {
        return ResponseEntity.ok(borrowService.createBorrow(request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateBorrow(@RequestBody BorrowRequest request) {
        return ResponseEntity.ok(borrowService.updateBorrow(request));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBorrow(@RequestParam UUID id) {
        return ResponseEntity.ok(borrowService.deleteBorrow(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(borrowService.getAllBorrows(page, size));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam UUID id) {
        return ResponseEntity.ok(borrowService.getBorrowById(id));
    }

    @PutMapping("/mark-as-paid")
    public ResponseEntity<?> markAsPaid(@RequestParam UUID id) {
        return ResponseEntity.ok(borrowService.markeBorrowAsPaid(id));
    }

}
