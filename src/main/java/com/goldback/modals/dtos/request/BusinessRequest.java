package com.goldback.modals.dtos.request;

import lombok.Data;

import java.util.UUID;

@Data
public class BusinessRequest {
    private UUID id;

    private String name;

    private String address;

    private String number;
}
