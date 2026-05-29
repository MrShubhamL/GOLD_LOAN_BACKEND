package com.goldback.repositories;

import com.goldback.modals.Permissions;
import com.goldback.modals.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, UUID> {
    Collection<Object> getPermissionsByRole(Role savedRole);
}
