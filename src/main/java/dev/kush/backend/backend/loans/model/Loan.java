package dev.kush.backend.backend.loans.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.backend.account.models.Account;
import dev.kush.backend.backend.loans.model.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private LoanType loanType;
    private Long loanAmount;
    private Float interest;

    @ManyToOne(cascade = ALL)
    @JsonManagedReference
    private Account account;

    public Loan(LoanType loanType, Long loanAmount, Float interest, Account account) {
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interest = interest;
        this.account = account;
    }


}
