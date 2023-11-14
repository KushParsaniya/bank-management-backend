package dev.kush.backend.backend.cards.debitCards.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendDebitCardReqWrapper {
    private String username;
    private String email;
    private Long accountId;
    private Long Balance;
    private Long requestId;

}
