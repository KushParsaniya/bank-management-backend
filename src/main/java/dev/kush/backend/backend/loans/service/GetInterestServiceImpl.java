package dev.kush.backend.backend.loans.service;

import dev.kush.backend.backend.loans.model.LoanType;
import org.springframework.stereotype.Service;

import static dev.kush.backend.backend.loans.model.LoanType.*;

@Service
public class GetInterestServiceImpl implements GetInterestService{
    @Override
    public Float getInterest(LoanType loanType) {
        if (loanType == PERSONAL){
            return 8.0F;
        } else if (loanType == HOME) {
            return 8.40F;
        } else if (loanType == BUSINESS) {
            return 11.0F;
        } else if (loanType == CAR){
            return 6.65F;
        } else if (loanType == STUDENT) {
            return 7.3F;
        }
        return 5.0F;
    }
}
