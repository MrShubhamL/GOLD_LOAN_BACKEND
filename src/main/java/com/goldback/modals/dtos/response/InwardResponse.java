package com.goldback.modals.dtos.response;


import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class InwardResponse {
    private UUID id;

    private String jewelleryName;

    private String type;

    private String carat;

    private Double grossWeight;

    private Double netWeight;

    private Double currentGoldRate;

    private Double moneyGiven;

    private Double interest;

    private String startDate;
    private String returnDate;
    private String createdAt;
    private String updatedAt;
    private String status;
    private Boolean isOwned;

    private OutwardResponse outwardResponse;
}
