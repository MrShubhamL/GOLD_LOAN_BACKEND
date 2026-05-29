package com.goldback.modals.dtos.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerRequest {
    private UUID id;
    private String name;
    private String contact;
    private String address;
}
