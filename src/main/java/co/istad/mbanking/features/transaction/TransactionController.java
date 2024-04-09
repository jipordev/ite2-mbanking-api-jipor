package co.istad.mbanking.features.transaction;

import co.istad.mbanking.features.transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.features.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    TransactionResponse transfer(@Valid @RequestBody TransactionCreateRequest request) {
        return service.transfer(request);
    }

    @GetMapping
    public Page<TransactionResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "") String transactionType
    ) {
        // Parse sorting parameters
        Sort.Direction direction = Sort.Direction.ASC;
        String[] sortParams = sort.split(":");
        if (sortParams.length == 2 && sortParams[1].equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }

        // Construct Sort object based on the sort parameter
        Sort sortObject = Sort.by(direction, "transactionAt");

        // Call service method with sorting and filtering parameters
        return service.findAll(page, size, sortObject, transactionType);
    }

}
