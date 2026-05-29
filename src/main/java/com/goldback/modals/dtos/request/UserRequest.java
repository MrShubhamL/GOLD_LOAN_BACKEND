package com.goldback.modals.dtos.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest {
    private UUID id;
    private String name;
    private String username;
    private String password;
}
