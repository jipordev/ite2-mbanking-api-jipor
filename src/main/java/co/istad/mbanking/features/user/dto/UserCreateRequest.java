package co.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record UserCreateRequest(
        @NotNull
        @Size(max = 9999, message = "Pin must be 4 digits")
        @Positive(message = "Pin must be positive")
        String pin,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20, message = "Phone number must be less than 20 digits")
        String phoneNumber,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Confirm password is required")
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

        @Size(max = 20)
        String studentIdCard,

        @NotEmpty
        List<RoleRequest> roles
) {
}
