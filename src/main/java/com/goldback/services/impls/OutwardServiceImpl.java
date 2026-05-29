package com.goldback.services.impls;

import com.goldback.modals.Business;
import com.goldback.modals.Inward;
import com.goldback.modals.Outward;
import com.goldback.modals.dtos.request.BusinessRequest;
import com.goldback.modals.dtos.request.OutwardRequest;
import com.goldback.modals.dtos.response.PaginatedResponse;
import com.goldback.repositories.BusinessRepository;
import com.goldback.repositories.InwardRepository;
import com.goldback.repositories.OutwardRepository;
import com.goldback.services.OutwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutwardServiceImpl implements OutwardService {

    private final OutwardRepository outwardRepository;
    private final BusinessRepository businessRepository;
    private final InwardServiceImpl inwardService;
    private final InwardRepository inwardRepository;
    private final PaginationResponseImpl paginationResponse;


    @Override
    public Boolean createOutward(OutwardRequest request) {
        return createOrUpdateOutward(request, null);
    }

    private Boolean createOrUpdateOutward(OutwardRequest request, Outward outward) {
        if (outward == null)
            outward = new Outward();

        if (request.getInwardRequest() != null) {
            Inward inward = inwardRepository
                    .findById(request.getInwardRequest().getId())
                    .orElseThrow(() -> new RuntimeException("Inward not found"));
            inward.setIsOwned(false);
            outward.setInward(inwardRepository.save(inward));
        } else {
            outward.setInward(null);
            outward.setCarat(request.getCarat());
            outward.setJewelleryName(request.getJewelleryName());
            outward.setWeight(request.getWeight());
        }

        outward.setCarat(request.getCarat());
        outward.setJewelleryName(request.getJewelleryName());
        outward.setWeight(request.getWeight());


        outward.setInterest(request.getInterest());
        outward.setMarketValuation(request.getMarketValuation());
        outward.setMoneyTaken(request.getMoneyTaken());
        outward.setReturnDate(request.getReturnDate());
        outward.setStatus("UNPAID");

        if (request.getBusiness().getId() == null)
            outward.setBusiness(createOrUpdateBusiness(request.getBusiness(), null));
        else
            outward.setBusiness(createOrUpdateBusiness(request.getBusiness(), businessRepository.findById(request.getBusiness().getId()).orElseThrow(() -> new RuntimeException("Business not found"))));

        outwardRepository.save(outward);
        return true;
    }

    private Business createOrUpdateBusiness(BusinessRequest request, Business business) {
        if (business == null) {
            business = new Business();
        }
        business.setName(request.getName());
        business.setAddress(request.getAddress());
        business.setNumber(request.getNumber());
        return businessRepository.save(business);
    }

    @Override
    public Boolean updateOutward(OutwardRequest request) {
        return createOrUpdateOutward(request, outwardRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Outward not found")));
    }

    @Override
    public OutwardRequest getOutwardById(UUID id) {
        return getOutwardResponse(outwardRepository.findById(id).orElseThrow(() -> new RuntimeException("Outward not found")));
    }

    public OutwardRequest getOutwardResponse(Outward outward) {
        OutwardRequest request = new OutwardRequest();
        request.setId(outward.getId());
        request.setCarat(outward.getCarat());
        request.setWeight(outward.getWeight());
        request.setJewelleryName(outward.getJewelleryName());
        request.setInterest(outward.getInterest());
        request.setMarketValuation(outward.getMarketValuation());
        request.setMoneyTaken(outward.getMoneyTaken());
        request.setReturnDate(outward.getReturnDate());
        request.setStatus(outward.getStatus());

        if (outward.getInward() != null)
            request.setInwardRequest(inwardService.inwardResponse(outward.getInward()));

        BusinessRequest businessRequest = new BusinessRequest();
        businessRequest.setId(outward.getBusiness().getId());
        businessRequest.setName(outward.getBusiness().getName());
        businessRequest.setAddress(outward.getBusiness().getAddress());
        businessRequest.setNumber(outward.getBusiness().getNumber());
        request.setBusiness(businessRequest);
        return request;
    }

    @Override
    @Transactional
    public Boolean deleteOutward(UUID id) {

        Outward outward = outwardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Outward not found"));

        if (!"UNPAID".equals(outward.getStatus())) {
            return false;
        }

        System.out.println("Deleting outward with ID: " + id);

        Inward inward = outward.getInward();

        if (inward != null) {

            inward.setIsOwned(true);

            // IMPORTANT if bidirectional mapping exists
            inward.setOutward(null);

            inwardRepository.save(inward);
        }

        // Break relations
        outward.setBusiness(null);
        outward.setInward(null);

        // DO NOT SAVE AGAIN
        outwardRepository.delete(outward);

        outwardRepository.flush();

        return true;
    }

    @Override
    public PaginatedResponse<OutwardRequest> getOutwards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Outward> outwardPage = outwardRepository.findAll(pageable);
        return paginationResponse.buildPaginatedResponse(outwardPage.getContent().stream().map(this::getOutwardResponse).toList(), outwardPage);
    }

    @Override
    public PaginatedResponse<OutwardRequest> getOutwardsByBusiness(UUID businessId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Outward> outwardPage = outwardRepository.findByBusinessId(businessId, pageable);
        return paginationResponse.buildPaginatedResponse(outwardPage.getContent().stream().map(this::getOutwardResponse).toList(), outwardPage);
    }

    @Override
    public OutwardRequest getOutwardsByInward(UUID inwardId) {
        return getOutwardResponse(outwardRepository.findByInwardId(inwardId).orElseThrow(() -> new RuntimeException("Outward not found")));
    }

    @Override
    public OutwardRequest collectOutward(UUID id) {
        Outward outward = outwardRepository.findById(id).orElseThrow(() -> new RuntimeException("Outward not found"));
        outward.setStatus("GIVEN");
        return getOutwardResponse(outwardRepository.save(outward));
    }

    @Override
    public Double totalMoneyGivenByBusiness() {
        return outwardRepository.sumMoneyGivenByBusiness();
    }

    @Override
    public Integer totalActiveOutwards() {
        return outwardRepository.findAll().stream().filter(outward -> outward.getStatus().equals("UNPAID")).toList().size();
    }

    @Override
    public Double totalInterestGiven() {
        if(outwardRepository.sumOfNotCollected() == null || outwardRepository.sumByInterest() == null || outwardRepository.sumByInterest() == 0) {
            return 0.0;
        }
        return (outwardRepository.sumOfNotCollected() * outwardRepository.sumByInterest()) / 100;
    }

    @Override
    public Double averageInterestGiven() {
        return outwardRepository.averageInterestGiven();
    }

}
