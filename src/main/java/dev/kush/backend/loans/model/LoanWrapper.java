package dev.kush.backend.loans.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanWrapper {
    private LoanType loanType;
    @Min(value = 0,message = "Loan amount should be greater than zero")
    private Long loanAmount;
    private Float loanInterest;
}
