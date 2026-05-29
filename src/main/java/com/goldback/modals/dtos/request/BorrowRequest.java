package com.goldback.modals.dtos.request;

import lombok.Data;

import java.util.UUID;

@Data
public class BorrowRequest {
    private UUID id;

    private String name;

    private String contact;

    private String address;

    private String borrowDate;

    private Double amountTaken;
    private String status;

    private Double interest;

    private String note;
}
