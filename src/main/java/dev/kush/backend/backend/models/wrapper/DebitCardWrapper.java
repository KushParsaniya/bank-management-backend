package dev.kush.backend.backend.models.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebitCardWrapper {
    private String cardNumber;
    private String expirationDate;
}
