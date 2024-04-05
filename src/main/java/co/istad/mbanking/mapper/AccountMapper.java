package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.Account;
import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toAccountResponse(Account account);
    List<AccountResponse> toAccountResponseList(List<Account> accounts);

    Account fromAccountCreateRequest(AccountCreateRequest request);

}
