package com.goldback.services;


import com.goldback.modals.Permissions;
import com.goldback.modals.Privilege;
import com.goldback.modals.Role;
import com.goldback.modals.dtos.request.RoleDto;
import com.goldback.modals.dtos.response.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role createRole(RoleDto roleDto);
    List<RoleDto> getAllRoles();

    List<Permissions> createPermissions(List<Permissions> permissions);

    Privilege createPrivilege(Privilege privilege);

    RoleDto getRoleByRoleName(String roleName);

    Role updateRole(RoleDto roleDto) ;

    Role findById(UUID roleId);

    ApiResponse<?> deleteRole(UUID roleId);
}
