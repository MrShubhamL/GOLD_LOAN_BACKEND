package com.goldback.services.impls;

import com.goldback.modals.Customer;
import com.goldback.modals.dtos.response.*;
import com.goldback.repositories.CustomerRepository;
import com.goldback.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PaginationResponseImpl paginationResponse;

    @Override
    public PaginatedResponse<CustomerResponse> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return paginationResponse.buildPaginatedResponse(customerPage.getContent().stream().map(this::customerResponse).toList(), customerPage);
    }

    public CustomerResponse customerResponse(Customer customer) {
        CustomerResponse request = new CustomerResponse();
        request.setId(customer.getId());
        request.setName(customer.getName());
        request.setContact(customer.getContact());
        request.setAddress(customer.getAddress());
        request.setInwards(
                customer.getInwards().stream().map(inward -> {
                    InwardResponse inwardResponse = new InwardResponse();
                    inwardResponse.setId(inward.getId());
                    inwardResponse.setCarat(inward.getCarat());
                    inwardResponse.setJewelleryName(inward.getJewelleryName());
                    inwardResponse.setInterest(inward.getInterest());
                    inwardResponse.setGrossWeight(inward.getGrossWeight());
                    inwardResponse.setCurrentGoldRate(inward.getCurrentGoldRate());
                    inwardResponse.setType(inward.getType());
                    inwardResponse.setMoneyGiven(inward.getMoneyGiven());
                    inwardResponse.setNetWeight(inward.getNetWeight());
                    inwardResponse.setStartDate(inward.getStartDate());
                    inwardResponse.setReturnDate(inward.getReturnDate());
                    inwardResponse.setCreatedAt(String.valueOf(inward.getCratedAt()));
                    inwardResponse.setUpdatedAt(String.valueOf(inward.getUpdatedAt()));
                    inwardResponse.setStatus(inward.getStatus());
                    inwardResponse.setIsOwned(inward.getIsOwned());
                    OutwardResponse outwardResponse = new OutwardResponse();
                    if (inward.getOutward() != null) {
                        outwardResponse.setId(inward.getOutward().getId());
                        outwardResponse.setCarat(inward.getOutward().getCarat());
                        outwardResponse.setJewelleryName(inward.getOutward().getJewelleryName());
                        outwardResponse.setCarat(inward.getOutward().getCarat());
                        outwardResponse.setWeight(inward.getOutward().getWeight());
                        outwardResponse.setInterest(inward.getOutward().getInterest());
                        outwardResponse.setMoneyTaken(inward.getOutward().getMoneyTaken());
                        outwardResponse.setStatus(inward.getOutward().getStatus());
                        outwardResponse.setReturnDate(inward.getOutward().getReturnDate());
                        outwardResponse.setCreatedAt(inward.getOutward().getCreatedAt());
                        BusinessResponse businessResponse = new BusinessResponse();
                        businessResponse.setId(inward.getOutward().getBusiness().getId());
                        businessResponse.setName(inward.getOutward().getBusiness().getName());
                        businessResponse.setAddress(inward.getOutward().getBusiness().getAddress());
                        outwardResponse.setBusinessResponse(businessResponse);
                        inwardResponse.setOutwardResponse(outwardResponse);
                    }
                    return inwardResponse;
                }).toList()
        );
        return request;
    }


    @Override
    public CustomerResponse getCustomerByContact(String contact) {
        return customerResponse(customerRepository.findCustomerByContact(contact).orElseThrow(() -> new RuntimeException("Customer not found")));
    }

    @Override
    public List<CustomerResponse> search(String keyword) {
        List<Customer> customers = customerRepository.searchCustomer(keyword);
        return customers.stream()
                .map(this::customerResponse)
                .toList();
    }

    @Override
    public Integer totalCustomers() {
        return customerRepository.findAll().size();
    }

    @Override
    public Integer countOfCustomers() {
        return customerRepository.countOfCustomers();
    }
}
