package dev.kush.backend.backend.loans.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendRequestLoan {
    private String username;
    private String email;
    private Long Balance;
    private Long accountId;
    private Long requestId;
    private Long appliedAmount;
    private Float loanInterest;
    private LoanType loanType;
}
