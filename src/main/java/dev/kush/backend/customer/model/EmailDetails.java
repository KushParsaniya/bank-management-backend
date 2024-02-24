package dev.kush.backend.customer.model;

public record EmailDetails(
        String recipient,
        String msgBody,
        String subject
) {
}
