package co.istad.mbanking.features.mail.dto;

import jakarta.validation.constraints.NotBlank;

public record MailResponse(

        String message

) {
}
