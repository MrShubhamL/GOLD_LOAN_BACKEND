package com.goldback.controllers;

import com.goldback.services.impls.FactoryResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
@CrossOrigin("*")
public class FactoryResetController {

    private final FactoryResetService factoryResetService;

    @Value("${app.factory-reset.secret}")
    private String resetSecret;

    @PostMapping("/factory-reset")
    public ResponseEntity<?> factoryReset(
            @RequestParam String secret
    ) {

        if (!resetSecret.equals(secret)) {
            return ResponseEntity.status(403)
                    .body("Invalid secret");
        }

        factoryResetService.resetDatabase();

        return ResponseEntity.ok(true);
    }
}