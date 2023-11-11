package dev.kush.backend.backend.models.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanWrapper {
    private String loanType;
    private Long loanAmount;
    private Float loanInterest;
}
