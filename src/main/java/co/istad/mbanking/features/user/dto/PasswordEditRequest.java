package co.istad.mbanking.features.user.dto;

public record PasswordEditRequest(
        String password,
        String newPassword,
        String confirmedPassword
) {
}
