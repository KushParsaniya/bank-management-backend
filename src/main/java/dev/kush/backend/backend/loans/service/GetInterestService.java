package dev.kush.backend.backend.loans.service;

import dev.kush.backend.backend.loans.model.LoanType;

public interface GetInterestService {
    Float getInterest(LoanType loanType);
}
