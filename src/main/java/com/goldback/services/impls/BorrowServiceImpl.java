package com.goldback.services.impls;

import com.goldback.modals.Borrow;
import com.goldback.modals.dtos.request.BorrowRequest;
import com.goldback.modals.dtos.response.PaginatedResponse;
import com.goldback.repositories.BorrowRepository;
import com.goldback.services.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRepository borrowRepository;
    private final PaginationResponseImpl paginationResponse;


    @Override
    public Boolean createBorrow(BorrowRequest request) {
        return createOrUpdateBorrow(request, null);
    }

    private Boolean createOrUpdateBorrow(BorrowRequest request, Borrow borrow) {
        if (borrow == null) borrow = new Borrow();

        borrow.setName(request.getName());
        borrow.setContact(request.getContact());
        borrow.setAddress(request.getAddress());
        borrow.setBorrowDate(request.getBorrowDate());
        borrow.setAmountTaken(request.getAmountTaken());
        borrow.setInterest(request.getInterest());
        borrow.setNote(request.getNote());
        borrow.setStatus("UNPAID");
        borrowRepository.save(borrow);
        return true;
    }

    @Override
    public Boolean updateBorrow(BorrowRequest request) {
        return createOrUpdateBorrow(request, borrowRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("Borrow not found")));
    }

    @Override
    public Boolean markeBorrowAsPaid(UUID id) {
        borrowRepository.findById(id).ifPresent(borrow -> {
            borrow.setStatus("PAID");
            borrowRepository.save(borrow);
        });
        return true;
    }

    @Override
    public Boolean deleteBorrow(UUID id) {
        if (borrowRepository.existsById(id)) {
            borrowRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public PaginatedResponse<BorrowRequest> getAllBorrows(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Borrow> borrowPage = borrowRepository.findAll(pageable);
        return paginationResponse.buildPaginatedResponse(borrowPage.getContent().stream().map(this::borrowResponse).toList(), borrowPage);
    }

    @Override
    public BorrowRequest getBorrowById(UUID id) {
        return borrowResponse(borrowRepository.findById(id).orElseThrow(() -> new RuntimeException("Borrow not found")));
    }

    @Override
    public Double totalBorrowedAmount() {
        return borrowRepository.sumAmountTaken();
    }

    @Override
    public Double totalInterest() {
        return (borrowRepository.sumAmountTaken() / borrowRepository.sumInterest()) * 100;
    }

    public BorrowRequest borrowResponse(Borrow borrow) {
        BorrowRequest request = new BorrowRequest();
        request.setId(borrow.getId());
        request.setName(borrow.getName());
        request.setContact(borrow.getContact());
        request.setAddress(borrow.getAddress());
        request.setBorrowDate(borrow.getBorrowDate());
        request.setAmountTaken(borrow.getAmountTaken());
        request.setInterest(borrow.getInterest());
        request.setNote(borrow.getNote());
        request.setStatus(borrow.getStatus());
        return request;
    }
}
