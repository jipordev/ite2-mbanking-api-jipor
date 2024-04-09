package co.istad.mbanking.features.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionCreateRequest(

        @NotBlank(message = "Account no is required")
        String accountOwner,
        @NotBlank(message = "Account name is required")
        String accountTransferReceiver,
        @NotNull(message = "Amount is required")
        @Positive
        BigDecimal amount,
        String remark

) {
}
