package dev.kush.backend.loans.service;

import dev.kush.backend.loans.model.LoanType;

public interface GetInterestService {
    Float getInterest(LoanType loanType);
}
