package dev.kush.backend.backend.cards.service;

public interface CardGeneratorService {
    String generateCardNumber();
    String generateCVV();

    String generateExpirationDate();

}
