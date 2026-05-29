package com.goldback.controllers;

import com.goldback.configuration.jwt.AppUserDetailsService;
import com.goldback.configuration.jwt.JwtUtils;
import com.goldback.modals.AppUser;
import com.goldback.modals.Permissions;
import com.goldback.modals.Privilege;
import com.goldback.modals.Role;
import com.goldback.modals.dtos.request.JwtRequest;
import com.goldback.modals.dtos.request.PermissionDto;
import com.goldback.modals.dtos.request.RoleDto;
import com.goldback.modals.dtos.response.JwtResponse;
import com.goldback.repositories.AppUserRepository;
import com.goldback.repositories.PermissionRepository;
import com.goldback.repositories.RoleRepository;
import com.goldback.services.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
@CrossOrigin("*")
public class JwtAuthenticationController {

    private final AppUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleService roleService;

    @PostConstruct
    public void createAdmin() {
        Optional<AppUser> optionalUser = userRepository.getMyUserByUsername("admin.com");
        if (optionalUser.isEmpty()) {
            Role savedRole = roleRepository.findByRoleName("SUPER_ADMIN")
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setRoleName("SUPER_ADMIN");
                        role.setRoleDescription("This is super admin role");
                        return roleRepository.save(role);
                    });

            if (permissionRepository.getPermissionsByRole(savedRole).isEmpty()) {
                Privilege privilege = new Privilege();
                privilege.setWritePermission("WRITE");
                privilege.setReadPermission("READ");
                privilege.setDeletePermission("DELETE");
                privilege.setUpdatePermission("UPDATE");

                Permissions permissions = new Permissions();
                permissions.setUserPermission("ALL_PERMISSIONS");
                permissions.setRole(savedRole);
                permissions.setPrivilege(privilege);
                roleService.createPermissions(List.of(permissions));
            }

            AppUser user = new AppUser();
            user.setName("Admin");
            user.setUsername("admin.com");
            user.setRole(savedRole);
            user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
            userRepository.save(user);
        }
    }

    @PostMapping("/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getUsername());

        AppUser user = userRepository
                .getMyUserByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Role> roleOptional = roleRepository.findById(user.getRole().getId());
        Role role = roleOptional.get();

        JwtResponse response = new JwtResponse();
        response.setUsername(userDetails.getUsername());
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setJwtToken(jwtUtils.generateToken(userDetails.getUsername()));
        response.setUserId(user.getId());


        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setRoleDescription(role.getRoleDescription());

        List<PermissionDto> permissionDtos = new ArrayList<>();

        for (Permissions permission : role.getPermissions()) {
            PermissionDto permissionDto = new PermissionDto();
            List<String> privileges = new ArrayList<>();
            permissionDto.setUserPermission(permission.getUserPermission());
            privileges.add(permission.getPrivilege().getReadPermission());
            privileges.add(permission.getPrivilege().getWritePermission());
            privileges.add(permission.getPrivilege().getUpdatePermission());
            privileges.add(permission.getPrivilege().getDeletePermission());
            permissionDto.setPrivileges(privileges);
            permissionDtos.add(permissionDto);
        }

        roleDto.setPermissions(permissionDtos);

        response.setRole(roleDto);

        return response;
    }

    @PostMapping("/is-token-expired")
    public ResponseEntity<?> idTokenExpired(@RequestBody JwtResponse jwtResponse) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(jwtUtils.isTokenExpired(jwtResponse.getJwtToken()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }

}
