package co.istad.mbanking.features.transaction;

import co.istad.mbanking.features.transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.features.transaction.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TransactionService {

    TransactionResponse transfer(TransactionCreateRequest request);

    Page<TransactionResponse> findAll(int page, int size, Sort sortParam, String transactionType);
}
