package dev.kush.backend.loans.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.account.models.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanRequest {
    @Id @GeneratedValue
    private Long id;
    @Min(value = 0,message = "Applied amount should be greater than zero")
    private Long appliedAmount;
    private Float loanInterest;

    @Enumerated(value = EnumType.STRING)
    private LoanType loanType;

    @ManyToOne(cascade = {DETACH,REFRESH,PERSIST,MERGE})
    @JsonManagedReference
    private Account account;

    public LoanRequest(Long appliedAmount, Float loanInterest, LoanType loanType) {
        this.appliedAmount = appliedAmount;
        this.loanInterest = loanInterest;
        this.loanType = loanType;
    }

    public LoanRequest(Long appliedAmount, Float loanInterest, LoanType loanType, Account account) {
        this.appliedAmount = appliedAmount;
        this.loanInterest = loanInterest;
        this.loanType = loanType;
        this.account = account;
    }
}
