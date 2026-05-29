package com.goldback.services;

import com.goldback.modals.dtos.request.InwardRequest;
import com.goldback.modals.dtos.response.ApiResponse;
import com.goldback.modals.dtos.response.PaginatedResponse;

import java.util.List;
import java.util.UUID;

public interface InwardService {

    Boolean createInward(InwardRequest request);

    Boolean updateInward(InwardRequest request);

    PaginatedResponse<InwardRequest> getInwards(int page , int size);

    InwardRequest getInward(UUID id);

    ApiResponse<?> delete(UUID id);

    PaginatedResponse<InwardRequest> getInwardsByCustomer(UUID customerId , int page , int size);

    List<InwardRequest> getAllInwardsByClosingSoon();

    List<InwardRequest> getAllInwardsByClosed();

    InwardRequest collectInward(UUID id);

    Double totalMoneyGiven();

    Double totalInterestEarned();

    Double totalMoneyCollected();

    Double notCollectedMoney();

    Integer totalActiveInwards();

    Double calculateProfit();
}
