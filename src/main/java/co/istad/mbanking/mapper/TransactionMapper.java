package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.Transaction;
import co.istad.mbanking.features.transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.features.transaction.dto.TransactionResponse;
import org.apache.catalina.LifecycleState;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponse toTransactionResponse(Transaction transaction);
    List<TransactionResponse> toTransactionResponseList(List<Transaction> transaction);
    Transaction fromTransactionCreateRequest(TransactionCreateRequest request);

}
