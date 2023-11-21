package dev.kush.backend.cards.service;

public interface CardGeneratorService {
    String generateCardNumber();
    String generateCVV();

    String generateExpirationDate();

}
