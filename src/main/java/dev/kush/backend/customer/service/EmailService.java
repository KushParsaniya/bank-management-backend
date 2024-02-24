package dev.kush.backend.customer.service;


import dev.kush.backend.customer.model.EmailDetails;

public interface EmailService {
    String sendMail(EmailDetails emailDetails);
}
