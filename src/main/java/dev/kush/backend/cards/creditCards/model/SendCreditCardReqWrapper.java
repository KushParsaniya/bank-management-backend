package dev.kush.backend.cards.creditCards.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendCreditCardReqWrapper {
    @Size(min = 2,message = "username should be at least 2 characters.")
    private String username;
    @Email(message = "invalid email address")
    private String email;
    private Long accountId;
    @Min(value = 0,message = "Balance should be greater than zero")
    private Long Balance;
    private Long requestId;
    @Min(value = 0,message = "Applied amount should be greater than zero")
    private Long appliedAmount;

}
