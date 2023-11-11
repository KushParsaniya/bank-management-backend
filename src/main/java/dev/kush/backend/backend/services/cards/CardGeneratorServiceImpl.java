package dev.kush.backend.backend.services.cards;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CardGeneratorServiceImpl implements CardGeneratorService {
    @Override
    public String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder("4");  // Start with the standard prefix for Visa cards
        Random random = new Random();
        for (int i = 1; i <= 15; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }

    @Override
    public String generateCVV() {
        Random random = new Random();
        int cvv = 100 + random.nextInt(900);  // Generate a random 3-digit CVV
        return String.valueOf(cvv);
    }
}
