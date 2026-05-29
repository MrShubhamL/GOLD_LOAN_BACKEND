package com.goldback.modals.dtos.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class BusinessResponse {
    private UUID id;

    private String name;

    private String address;

    private String number;

    private List<OutwardResponse> outwards;
}
