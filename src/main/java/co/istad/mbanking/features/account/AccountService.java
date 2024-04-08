package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountRenameRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import co.istad.mbanking.features.account.dto.AccountTransferLimitRequest;
import co.istad.mbanking.features.user.dto.UserCreateRequest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public interface AccountService {

    void createNew(AccountCreateRequest accountCreateRequest);

    AccountResponse findByActNo(String actNo);

    AccountResponse renameByActNo(String actNo, AccountRenameRequest request);

    void hideAccount(String actNo);

    Page<AccountResponse> findAll(int page, int size);

    void updateTransferLimit(String actNo, AccountTransferLimitRequest request);
}
