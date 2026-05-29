package com.goldback.services;

import com.goldback.modals.dtos.request.CustomerRequest;
import com.goldback.modals.dtos.response.CustomerResponse;
import com.goldback.modals.dtos.response.PaginatedResponse;

import java.util.List;

public interface CustomerService {

    PaginatedResponse<CustomerResponse> getAllCustomers(int page, int size);



    CustomerResponse getCustomerByContact(String contact);

    List<CustomerResponse> search(String keyword);

    Integer totalCustomers();

    Integer countOfCustomers();
}
