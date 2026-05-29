package com.goldback.controllers;

import com.goldback.services.BorrowService;
import com.goldback.services.InwardService;
import com.goldback.services.OutwardService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard-kpi")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReportKpiController {

    private final InwardService service;
    private final OutwardService outwardService;
    private final BorrowService borrowService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/total-money-given")
    public ResponseEntity<?> totalMoneyGiven() {
        return ResponseEntity.ok(service.totalMoneyGiven());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/total-interest-earned")
    public ResponseEntity<?> totalInterestEarned() {
        return ResponseEntity.ok(service.totalInterestEarned());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/total-money-collected")
    public ResponseEntity<?> totalMoneyCollected() {
        return ResponseEntity.ok(service.totalMoneyCollected());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/not-collected-money")
    public ResponseEntity<?> notCollectedMoney() {
        return ResponseEntity.ok(service.notCollectedMoney());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/total-interest-given")
    public ResponseEntity<?> totalMarketValuation() {
        return ResponseEntity.ok(outwardService.totalInterestGiven());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/average-interest-given")
    public ResponseEntity<?> averageInterestGiven() {
        return ResponseEntity.ok(outwardService.averageInterestGiven());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/total-money-given-by-business")
    public ResponseEntity<?> totalMoneyGivenByBusiness() {
        return ResponseEntity.ok(outwardService.totalMoneyGivenByBusiness());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/total-money-borrowed")
    public ResponseEntity<?> totalMoneyBorrowed() {
        return ResponseEntity.ok(borrowService.totalBorrowedAmount());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/total-interest-borrowed")
    public ResponseEntity<?> totalMoneyRepaid() {
        return ResponseEntity.ok(borrowService.totalInterest());
    }


}
