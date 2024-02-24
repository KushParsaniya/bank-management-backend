package dev.kush.backend.customer.dto;

import dev.kush.backend.customer.dto.SendDetailDto;

public record SendDetailDtoWithJwt(
        SendDetailDto data,
        String jwt
) {
}
