package co.istad.mbanking.auth.dto;

public record AuthResponse(
        String type,
        String accessToken,
        String refreshToken

) {
}
