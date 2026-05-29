package com.goldback.services.impls;

import com.goldback.modals.Customer;
import com.goldback.modals.Inward;
import com.goldback.modals.dtos.request.CustomerRequest;
import com.goldback.modals.dtos.request.InwardRequest;
import com.goldback.modals.dtos.response.ApiResponse;
import com.goldback.modals.dtos.response.PaginatedResponse;
import com.goldback.repositories.CustomerRepository;
import com.goldback.repositories.InwardRepository;
import com.goldback.services.InwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InwardServiceImpl implements InwardService {

    private final InwardRepository inwardRepository;
    private final CustomerRepository customerRepository;
    private final PaginationResponseImpl paginationResponse;


    @Override
    public Boolean createInward(InwardRequest request) {
        return createOrUpdateInward(null, request);
    }


    private Boolean createOrUpdateInward(Inward inward, InwardRequest request) {
        if (inward == null) inward = new Inward();
        inward.setJewelleryName(request.getJewelleryName());
        inward.setType(request.getType());
        inward.setGrossWeight(request.getGrossWeight());
        inward.setNetWeight(request.getNetWeight());
        inward.setCarat(request.getCarat());
        inward.setCurrentGoldRate(request.getCurrentGoldRate());
        inward.setMoneyGiven(request.getMoneyGiven());
        inward.setInterest(request.getInterest());
        inward.setStartDate(request.getStartDate());
        inward.setReturnDate(request.getReturnDate());
        inward.setStatus("PENDING");
        inward.setIsOwned(true);

        if (request.getCustomerRequest().getId() != null)
            inward.setCustomer(customerCreateOrUpdate(
                    customerRepository.findById(request.getCustomerRequest().getId())
                            .orElseThrow(() -> new RuntimeException("Customer not Found")),
                    request.getCustomerRequest())
            );
        else
            inward.setCustomer(customerCreateOrUpdate(null, request.getCustomerRequest()));

        inwardRepository.save(inward);
        return true;
    }

    private Customer customerCreateOrUpdate(Customer customer, CustomerRequest request) {
        if (customer == null) customer = new Customer();
        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer.setContact(request.getContact());

        return customerRepository.save(customer);
    }

    @Override
    public Boolean updateInward(InwardRequest request) {
        return createOrUpdateInward(inwardRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Inward Not Found")), request);
    }

    @Override
    public PaginatedResponse<InwardRequest> getInwards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Inward> inwardPage = inwardRepository.findAll(pageable);
        return paginationResponse.buildPaginatedResponse(inwardPage.getContent().stream().map(this::inwardResponse).toList(), inwardPage);
    }

    @Override
    public InwardRequest getInward(UUID id) {
        return inwardResponse(inwardRepository.findById(id).orElseThrow(() -> new RuntimeException("Inward Not Found")));
    }

    @Override
    public ApiResponse<?> delete(UUID id) {
        if (inwardRepository.existsById(id)) {
            inwardRepository.deleteById(id);
            return new ApiResponse<>(true, "Inward Deleted Successfully", null, HttpStatus.OK);
        }
        return new ApiResponse<>(false, "Inward Not", null, HttpStatus.BAD_REQUEST);
    }


    @Override
    public PaginatedResponse<InwardRequest> getInwardsByCustomer(UUID customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Inward> inwardPage = inwardRepository.getInwardsByCustomer_Id(customerId, pageable);
        return paginationResponse.buildPaginatedResponse(inwardPage.getContent().stream().map(this::inwardResponse).toList(), inwardPage);
    }

    @Override
    public List<InwardRequest> getAllInwardsByClosingSoon() {

        LocalDate today = LocalDate.now();
        LocalDate closingDate = today.plusDays(7);

        return inwardRepository.findAll().stream()
                .filter(inward -> {

                    if (inward.getReturnDate() == null) {
                        return false;
                    }

                    LocalDate returnDate = LocalDate.parse(inward.getReturnDate());

                    return returnDate.isAfter(today) &&
                            !returnDate.isAfter(closingDate) &&
                            inward.getStatus().equals("PENDING");
                })
                .map(this::inwardResponse)
                .toList();
    }

    @Override
    public List<InwardRequest> getAllInwardsByClosed() {
        LocalDate todayDate = LocalDate.now();
        return inwardRepository.findAll().stream()
                .filter(inward -> {
                    if (inward.getReturnDate() == null) {
                        return false;
                    }
                    LocalDate returnDate = LocalDate.parse(
                            inward.getReturnDate(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    );
                    return returnDate.isEqual(todayDate)
                            && inward.getStatus().equals("PENDING");
                })
                .map(this::inwardResponse)
                .toList();
    }

    @Override
    public InwardRequest collectInward(UUID id) {
        Inward inward = inwardRepository.findById(id).orElseThrow(() -> new RuntimeException("Inward Not Found"));
        inward.setStatus("COLLECTED");
        inward.setReturnDate(LocalDate.now().toString());
        return inwardResponse(inwardRepository.save(inward));
    }

    @Override
    public Double totalMoneyGiven() {
        return inwardRepository.sumMoneyGiven();
    }

    @Override
    public Double totalInterestEarned() {

        double totalInterest = 0.0;

        List<Inward> inwardList = inwardRepository.findAll();

        LocalDate today = LocalDate.now();

        for (Inward inward : inwardList) {

            // Only calculate for COLLECTED status
            if (!"COLLECTED".equalsIgnoreCase(inward.getStatus())) {
                continue;
            }

            LocalDate startDate = LocalDate.parse(inward.getStartDate());
            LocalDate returnDate = LocalDate.parse(inward.getReturnDate());

            // ==============================
            // Interest from Start -> Return
            // ==============================

            int returnMonths =
                    (returnDate.getYear() - startDate.getYear()) * 12
                            + (returnDate.getMonthValue() - startDate.getMonthValue());

            returnMonths = Math.max(returnMonths, 0);

            // ==============================
            // Additional Interest after Return Date
            // Return Date -> Today
            // ==============================

            int extraMonths = 0;

            if (today.isAfter(returnDate)) {

                extraMonths =
                        (today.getYear() - returnDate.getYear()) * 12
                                + (today.getMonthValue() - returnDate.getMonthValue());

                extraMonths = Math.max(extraMonths, 0);
            }

            int totalMonths = returnMonths + extraMonths;

            // Yearly -> Monthly Interest
            double monthlyInterestRate = inward.getInterest() / 12.0;

            // Final Interest
            double interest =
                    (inward.getMoneyGiven() * monthlyInterestRate * totalMonths) / 100;

            totalInterest += interest;
        }

        return Math.round(totalInterest * 100.0) / 100.0;
    }

    @Override
    public Double totalMoneyCollected() {
        return inwardRepository.sumOfMoneyGivenByStatus();
    }

    @Override
    public Double notCollectedMoney() {
        return inwardRepository.sumOfNotCollectedMoney();
    }

    @Override
    public Integer totalActiveInwards() {
        return inwardRepository.findAll().stream().filter(inward -> inward.getStatus().equals("PENDING")).toList().size();
    }

    @Override
    public Double calculateProfit() {
        double totalProfit = 0.0;
        List<Inward> inwardList = inwardRepository.findInwardByIsOwnedTrue();
        for (Inward inward : inwardList) {
            double profit = (inward.getMoneyGiven() * inward.getInterest()) / 100;
            totalProfit += profit;
        }
        List<Inward> inwards = inwardRepository.findInwardByIsOwnedFalse();
        for (Inward inward : inwards) {
            Double inwardInterest = inward.getInterest();
            Double outwardInterest = inward.getOutward().getInterest();

            if (inwardInterest != null && outwardInterest != null) {
                double profit = (inward.getMoneyGiven() * (inwardInterest - outwardInterest)) / 100;
                totalProfit += profit;
            }
        }
        return totalProfit;
    }

    public InwardRequest inwardResponse(Inward inward) {
        InwardRequest response = new InwardRequest();
        if (inward == null) return response;
        response.setId(inward.getId());
        response.setJewelleryName(inward.getJewelleryName());
        response.setType(inward.getType());
        response.setGrossWeight(inward.getGrossWeight());
        response.setNetWeight(inward.getNetWeight());
        response.setCarat(inward.getCarat());
        response.setCurrentGoldRate(inward.getCurrentGoldRate());
        response.setMoneyGiven(inward.getMoneyGiven());
        response.setInterest(inward.getInterest());
        response.setStartDate(inward.getStartDate());
        response.setReturnDate(inward.getReturnDate());
        response.setStatus(inward.getStatus());
        response.setCratedAt(inward.getCratedAt());
        response.setUpdatedAt(inward.getUpdatedAt());
        response.setIsOwned(inward.getIsOwned());

        Customer inwardCustomer = inward.getCustomer();
        if (inwardCustomer != null) {
            CustomerRequest customerRequest = new CustomerRequest();
            customerRequest.setId(inwardCustomer.getId());
            customerRequest.setName(inwardCustomer.getName());
            customerRequest.setAddress(inwardCustomer.getAddress());
            customerRequest.setContact(inwardCustomer.getContact());

            response.setCustomerRequest(customerRequest);
        }
        return response;
    }
}
