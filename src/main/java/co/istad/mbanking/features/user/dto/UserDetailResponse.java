package co.istad.mbanking.features.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserDetailResponse(
        String uuid,
        String nationalIdCard,
        String phoneNumber,
        String name,
        String profileImage,
        String gender,
        LocalDate dob
) {
}
