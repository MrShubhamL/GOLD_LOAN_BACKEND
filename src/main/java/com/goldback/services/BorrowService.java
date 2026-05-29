package com.goldback.services;

import com.goldback.modals.dtos.request.BorrowRequest;
import com.goldback.modals.dtos.response.PaginatedResponse;

import java.util.UUID;

public interface BorrowService {

    Boolean createBorrow(BorrowRequest request);

    Boolean updateBorrow(BorrowRequest request);

    Boolean markeBorrowAsPaid(UUID id);

    Boolean deleteBorrow(UUID id);

    PaginatedResponse<BorrowRequest> getAllBorrows(Integer page, Integer size);

    BorrowRequest getBorrowById(UUID id);

    Double totalBorrowedAmount();

     Double totalInterest();
}
