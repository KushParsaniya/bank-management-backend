package dev.kush.backend.customer.model;

import dev.kush.backend.account.models.AccountType;
import dev.kush.backend.account.models.TransactionWrapper;

import java.util.List;

public record SendDetailWrapper(
        String username,
        String email,
        Long accountId,
        Long Balance,
        AccountType type,
        Role role,
        List<TransactionWrapper>transactions) {
}
