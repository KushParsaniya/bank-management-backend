package dev.kush.backend.loans.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.kush.backend.account.models.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private LoanType loanType;
    @Min(value = 0,message = "Loan amount should be greater than zero")
    private Long loanAmount;
    private Float interest;

    @ManyToOne(cascade =  {DETACH,MERGE,REFRESH,PERSIST})
    @JsonManagedReference
    private Account account;

    public Loan(LoanType loanType, Long loanAmount, Float interest, Account account) {
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interest = interest;
        this.account = account;
    }


}
