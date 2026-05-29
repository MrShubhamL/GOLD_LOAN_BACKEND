package com.goldback.controllers;

import com.goldback.modals.dtos.request.InwardRequest;
import com.goldback.services.InwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inward")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InwardController {

    private final InwardService inwardService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody InwardRequest request) {
        return ResponseEntity.ok(inwardService.createInward(request));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody InwardRequest request) {
        return ResponseEntity.ok(inwardService.updateInward(request));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getInward(@RequestParam UUID id) {
        return ResponseEntity.ok(inwardService.getInward(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam UUID id) {
        return ResponseEntity.ok(inwardService.delete(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(inwardService.getInwards(page, size));
    }

    @GetMapping("/get-by-customer")
    public ResponseEntity<?> getByCustomer(@RequestParam UUID customerId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(inwardService.getInwardsByCustomer(customerId, page, size));
    }

    @PutMapping("/collect")
    public ResponseEntity<?> collect(@RequestParam UUID id) {
        return ResponseEntity.ok(inwardService.collectInward(id));
    }

    @GetMapping("/get-all-by-closing-soon")
    public ResponseEntity<?> getAllByClosingSoon() {
        return ResponseEntity.ok(inwardService.getAllInwardsByClosingSoon());
    }

    @GetMapping("/get-all-by-closed")
    public ResponseEntity<?> getAllByClosed() {
        return ResponseEntity.ok(inwardService.getAllInwardsByClosed());
    }

}