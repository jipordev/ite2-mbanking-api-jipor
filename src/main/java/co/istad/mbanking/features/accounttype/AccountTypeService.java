package co.istad.mbanking.features.accounttype;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.features.accounttype.dto.AccountTypeResponse;

import java.util.LinkedList;
import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> findAccountTypeList();
    AccountTypeResponse findAccountTypeByAlias(String alias);
}
