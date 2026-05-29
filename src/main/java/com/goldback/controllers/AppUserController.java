package com.goldback.controllers;

import com.goldback.modals.AppUser;
import com.goldback.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app-user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AppUserController {
    private final AppUserService appUserService;

    @PutMapping("/update")
    public ResponseEntity<Boolean> updateAppUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.updateAppUser(appUser));
    }
}
