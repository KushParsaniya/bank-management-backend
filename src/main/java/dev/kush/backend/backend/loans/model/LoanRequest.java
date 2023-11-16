package dev.kush.backend.backend.loans.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.backend.account.models.Account;
import jakarta.persistence.*;
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
