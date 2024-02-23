package dev.kush.backend.customer.model;

public record SendDetailWrapperWithJwt(
        SendDetailWrapper data,
        String jwt
) {
}
