package co.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record UserCreateRequest(
        @Size(max = 6, min = 6, message = "Pin can only be 6 digit")
        @Pattern(regexp = "\\d+", message = "Pin can only be 6 digit")
        String pin,

        @NotBlank
        @Size(max = 20)
        String phoneNumber,

        @NotBlank
        String password,

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

        @Size(max = 20)
        String studentIdCard,

        @NotEmpty
        List<RoleRequest> roles
) {
}
