package dev.kush.backend.customer.service.impl;

import dev.kush.backend.customer.model.ConfirmationToken;
import dev.kush.backend.customer.repository.ConfirmationTokenRepository;
import dev.kush.backend.customer.service.ConformationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConformationTokenServiceImpl implements ConformationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConformationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void saveToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAtByToken(LocalDateTime.now(),token);
    }
}
