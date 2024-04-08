package co.istad.mbanking.features.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AccountTransferLimitRequest(
        @NotNull
        BigDecimal transferLimit
) {
}
