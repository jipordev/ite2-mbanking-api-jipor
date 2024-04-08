package co.istad.mbanking.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRenameRequest(

        @NotBlank(message = "Rename is required")
                @Size(message = "Account name must be less than or equal to 100 character")
        String newName

) {
}
