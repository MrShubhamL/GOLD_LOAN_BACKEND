package com.goldback.modals.dtos.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CustomerResponse {
    private UUID id;
    private String name;
    private String contact;
    private String address;
    private List<InwardResponse> inwards;
}
