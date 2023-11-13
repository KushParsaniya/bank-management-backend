package dev.kush.backend.backend.loans.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceivedLoanWrapper {
    private LoanType loanType;
    private Long loanAmount;
    private Float loanInterest;
    private Long accountId;
}
