package dev.kush.backend.cards.creditCards.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendCreditCardReqWrapper {
    private String username;
    private String email;
    private Long accountId;
    private Long Balance;
    private Long requestId;
    private Long appliedAmount;

}
