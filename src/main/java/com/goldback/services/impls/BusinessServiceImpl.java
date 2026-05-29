package com.goldback.services.impls;

import com.goldback.modals.Business;
import com.goldback.modals.dtos.response.BusinessResponse;
import com.goldback.modals.dtos.response.OutwardResponse;
import com.goldback.modals.dtos.response.PaginatedResponse;
import com.goldback.repositories.BusinessRepository;
import com.goldback.services.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {


    private final BusinessRepository businessRepository;
    private final PaginationResponseImpl paginationResponse;
    private final InwardServiceImpl inwardService;


    @Override
    public PaginatedResponse<BusinessResponse> getAllBusinesses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Business> businessPage = businessRepository.findAll(pageable);
        return paginationResponse.buildPaginatedResponse(businessPage.getContent().stream().map(this::businessResponseFromBusiness).toList(), businessPage);
    }


    public BusinessResponse businessResponseFromBusiness(Business business) {
        BusinessResponse response = new BusinessResponse();
        response.setId(business.getId());
        response.setName(business.getName());
        response.setNumber(business.getNumber());
        response.setAddress(business.getAddress());
        response.setOutwards(
                business.getOutwards().stream().map(outward -> {
                    OutwardResponse outwardResponse = new OutwardResponse();
                    outwardResponse.setId(outward.getId());
                    outwardResponse.setCarat(outward.getCarat());
                    outwardResponse.setJewelleryName(outward.getJewelleryName());
                    outwardResponse.setWeight(outward.getWeight());
                    outwardResponse.setInterest(outward.getInterest());
                    outwardResponse.setMarketValuation(outward.getMarketValuation());
                    outwardResponse.setMoneyTaken(outward.getMoneyTaken());
                    outwardResponse.setReturnDate(outward.getReturnDate());
                    outwardResponse.setStatus(outward.getStatus());
                    outwardResponse.setInwardRequest(inwardService.inwardResponse(outward.getInward()));
                    return outwardResponse;
                }).toList()
        );
        return response;
    }

    @Override
    public BusinessResponse getBusinessByContact(String contact) {
        return businessRepository.findByNumber(contact).map(this::businessResponseFromBusiness).orElseThrow(() -> new RuntimeException("Business not found"));
    }

    @Override
    public BusinessResponse updateBusiness(BusinessResponse businessResponse) {
        Business business = businessRepository.findById(businessResponse.getId()).orElseThrow(() -> new RuntimeException("Business not found"));
        business.setName(businessResponse.getName());
        business.setAddress(businessResponse.getAddress());
        Business save = businessRepository.save(business);
        return businessResponseFromBusiness(save);
    }

    @Override
    public Boolean deleteBusiness(UUID id) {
        Business business = businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Business not found"));
        if(business.getOutwards().isEmpty()) {
            businessRepository.delete(business);
            return true;
        }
        return false;
    }

    @Override
    public List<BusinessResponse> search(String keyword) {
        List<Business> business = businessRepository.searchBusiness(keyword);

        return business.stream()
                .map(this::businessResponseFromBusiness)
                .toList();
    }

    @Override
    public Integer countOfBusinesses() {
        return businessRepository.countOfBusinesses();
    }
}
