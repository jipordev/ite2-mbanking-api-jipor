package co.istad.mbanking.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "refresh token is required")
        String refreshToken
) {
}
