package com.goldback.services;

import com.goldback.modals.dtos.response.BusinessResponse;
import com.goldback.modals.dtos.response.PaginatedResponse;

import java.util.List;
import java.util.UUID;

public interface BusinessService {

    PaginatedResponse<BusinessResponse> getAllBusinesses(int page, int size);

    BusinessResponse getBusinessByContact(String contact);

    BusinessResponse updateBusiness(BusinessResponse businessResponse);
    Boolean deleteBusiness(UUID id);

    List<BusinessResponse> search(String keyword);

    Integer countOfBusinesses();
}
