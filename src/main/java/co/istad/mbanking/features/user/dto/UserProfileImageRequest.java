package co.istad.mbanking.features.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserProfileImageRequest(
        @NotNull
        String mediaName
) {
}
