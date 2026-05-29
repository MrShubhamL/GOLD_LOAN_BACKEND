package com.goldback.services.impls;

import com.goldback.modals.AppUser;
import com.goldback.repositories.AppUserRepository;
import com.goldback.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public Boolean updateAppUser(AppUser appUser) {
        System.out.println("App User ID: " + appUser.getId());
        appUserRepository.findById(appUser.getId()).ifPresent(existingUser -> {
            existingUser.setName(appUser.getName());
            existingUser.setUsername(appUser.getUsername());
            if(appUser.getPassword() != null && !appUser.getPassword().isEmpty()) {
                existingUser.setPassword(new BCryptPasswordEncoder().encode(appUser.getPassword()));
            }
            appUserRepository.save(existingUser);
        });
        return true;
    }
}
