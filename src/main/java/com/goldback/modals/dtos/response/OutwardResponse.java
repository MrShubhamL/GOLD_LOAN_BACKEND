package com.goldback.modals.dtos.response;

import com.goldback.modals.dtos.request.InwardRequest;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class OutwardResponse {

    private UUID id;

    private String jewelleryName;

    private String weight;

    private String carat;

    private Double marketValuation;

    private Double moneyTaken;

    private Double interest;

    private String returnDate;

    private String status;

    private InwardRequest inwardRequest;

    private BusinessResponse businessResponse;

    private LocalDate createdAt;
}
