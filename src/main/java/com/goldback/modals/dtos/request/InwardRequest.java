package com.goldback.modals.dtos.request;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class InwardRequest {
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

    private String status;

    private Boolean isOwned;

    private LocalDateTime cratedAt;

    private LocalDateTime updatedAt;

    private CustomerRequest customerRequest;
}
