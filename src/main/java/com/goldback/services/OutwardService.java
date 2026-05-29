package com.goldback.services;

import com.goldback.modals.dtos.request.OutwardRequest;
import com.goldback.modals.dtos.response.PaginatedResponse;

import java.util.UUID;

public interface OutwardService {


    Boolean createOutward(OutwardRequest request);


    Boolean updateOutward(OutwardRequest request);


    OutwardRequest getOutwardById(UUID id);

    Boolean deleteOutward(UUID id);

    PaginatedResponse<OutwardRequest> getOutwards(int page, int size);

    PaginatedResponse<OutwardRequest> getOutwardsByBusiness(UUID businessId, int page, int size);

    OutwardRequest getOutwardsByInward(UUID inwardId);

    Double totalMoneyGivenByBusiness();

    Integer totalActiveOutwards();

    Double totalInterestGiven();

    OutwardRequest collectOutward(UUID id);

    Double averageInterestGiven();

}
