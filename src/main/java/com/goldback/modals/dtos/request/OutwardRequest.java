package com.goldback.modals.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class OutwardRequest {
    private UUID id;

    private String jewelleryName;

    private String weight;

    private String carat;

    private Double marketValuation;

    private Double moneyTaken;

    private Double interest;

    private String returnDate;

    private BusinessRequest business;

    private InwardRequest inwardRequest;

    private String status;
}
