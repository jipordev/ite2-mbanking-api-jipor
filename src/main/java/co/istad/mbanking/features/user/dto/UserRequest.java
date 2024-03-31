package co.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequest(
        @NotNull
        @Max(9999)
        @Positive
        Integer pin,
        @NotBlank
        @Size(max = 20)
        String phoneNumber,
        @NotBlank
        String password,
        String newPassword,
        @NotBlank
        String confirmedPassword,
        @NotBlank
        @Size(max = 40)
        String name,
        @NotBlank
        @Size(max = 6)
        String gender,
        @NotNull
        LocalDate dob,
        @NotBlank
        @Size(max = 20)
        String nationalCardId,
        @NotBlank
        @Size(max = 20)
        String studentId

) {
}
