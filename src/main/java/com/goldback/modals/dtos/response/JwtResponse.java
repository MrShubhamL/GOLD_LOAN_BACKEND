package com.goldback.modals.dtos.response;

import com.goldback.modals.dtos.request.RoleDto;
import lombok.Data;

import java.util.UUID;

@Data
public class JwtResponse {
    private UUID userId;
    private String username;
    private String name;
    private String jwtToken;
    private RoleDto role;
}