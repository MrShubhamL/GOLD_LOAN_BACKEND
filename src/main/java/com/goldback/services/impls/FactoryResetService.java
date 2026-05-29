package com.goldback.services.impls;

import com.goldback.configuration.jwt.AppUserDetailsService;
import com.goldback.configuration.jwt.JwtUtils;
import com.goldback.modals.AppUser;
import com.goldback.modals.Permissions;
import com.goldback.modals.Privilege;
import com.goldback.modals.Role;
import com.goldback.repositories.AppUserRepository;
import com.goldback.repositories.PermissionRepository;
import com.goldback.repositories.RoleRepository;
import com.goldback.services.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FactoryResetService {

    private final JdbcTemplate jdbcTemplate;
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleService roleService;

    @Transactional
    public void resetDatabase() {

        // Disable FK checks
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");

        // Truncate tables
        jdbcTemplate.execute("TRUNCATE TABLE outward");
        jdbcTemplate.execute("TRUNCATE TABLE inward");
        jdbcTemplate.execute("TRUNCATE TABLE business");
        jdbcTemplate.execute("TRUNCATE TABLE customer");
        jdbcTemplate.execute("TRUNCATE TABLE borrow");

        // Enable FK checks
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");

        // Optional default data
//        createAdmin();
    }

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
}
