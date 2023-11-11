package dev.kush.backend.backend.models.wrapper;

import dev.kush.backend.backend.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionWrapper {
    private String date;
    private String time;
    private TransactionType description;
    private Long amount;
}
