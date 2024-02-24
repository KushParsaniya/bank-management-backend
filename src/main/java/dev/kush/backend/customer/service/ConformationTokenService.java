package dev.kush.backend.customer.service;


import dev.kush.backend.customer.model.ConfirmationToken;

import java.util.Optional;

public interface ConformationTokenService {
    void saveToken(ConfirmationToken token);

    Optional<ConfirmationToken> getToken(String token);

    int setConfirmedAt(String token);
}
