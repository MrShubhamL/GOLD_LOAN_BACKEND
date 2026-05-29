package com.goldback.controllers;

import com.goldback.services.BorrowService;
import com.goldback.services.CustomerService;
import com.goldback.services.InwardService;
import com.goldback.services.OutwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DashboardController {

    private final CustomerService customerService;
    private final InwardService inwardService;
    private final OutwardService outwardService;
    private final BorrowService borrowService;

    @GetMapping("/total-customers")
    public Integer getTotalCustomers() {
        return customerService.totalCustomers();
    }

    @GetMapping("/total-active-inwards")
    public Integer getTotalActiveInwards() {
        return inwardService.totalActiveInwards();
    }

    @GetMapping("/total-active-outwards")
    public Integer getTotalActiveOutwards() {
        return outwardService.totalActiveOutwards();
    }

    @GetMapping("/total-borrows-amount")
    public Double getTotalBorrowedAmount() {
        return borrowService.totalBorrowedAmount();
    }

}
