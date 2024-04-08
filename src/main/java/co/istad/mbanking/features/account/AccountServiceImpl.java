package co.istad.mbanking.features.account;

import co.istad.mbanking.domain.Account;
import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.domain.UserAccount;
import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountRenameRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import co.istad.mbanking.features.account.dto.AccountTransferLimitRequest;
import co.istad.mbanking.features.accounttype.AccountTypeRepository;
import co.istad.mbanking.features.user.UserRepository;
import co.istad.mbanking.mapper.AccountMapper;
import co.istad.mbanking.util.RandomUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountTypeRepository accountTypeRepository;
    private final UserAccountRepository userAccountRepository;
    private final UserRepository userRepository;

    @Override
    public void createNew(AccountCreateRequest accountCreateRequest) {

        // check account type
        AccountType accountType = accountTypeRepository.findByAlias(accountCreateRequest.accountTypeAlias()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account type has not been found!"
                )
        );

        // check user by uuid
        User user = userRepository.findByUuid(accountCreateRequest.userUuid()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found"
                )
        );

        // map account dto to account entity
        Account account = accountMapper.fromAccountCreateRequest(accountCreateRequest);
        log.info("Account: {}",account.toString());
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setActNo(RandomUtil.generateNineDigitString());
        account.setTransferLimit(BigDecimal.valueOf(5000));
        account.setIsHidden(false);

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setUser(user);
        userAccount.setIsDeleted(false);
        userAccount.setIsBlocked(false);
        userAccount.setCreatedAt(LocalDateTime.now());

        userAccountRepository.save(userAccount);

    }

    @Override
    public AccountResponse findByActNo(String actNo) {

        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Account no is invalid"
                        )
                );

        return accountMapper.toAccountResponse(account);

    }

    @Override
    public AccountResponse renameByActNo(String actNo, AccountRenameRequest request) {

        // load the account by actNo
        Account account = accountRepository.findByActNo(actNo).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account has not been found"
                )
        );

        // check if alias the same as newName
        if (account.getAlias() != null && account.getAlias().equals(request.newName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "New name can't be the same as old name"
            );
        }

        account.setAlias(request.newName());

        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);

    }

    @Transactional
    @Override
    public void hideAccount(String actNo) {
        if (!accountRepository.existsByActNo(actNo)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account has not been found"
            );
        }

        try {
            accountRepository.hideAccountByActNo(actNo);

        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong"
            );
        }
    }

    @Override
    public Page<AccountResponse> findAll(int page, int size) {

        // validate page and size
        if (page < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page number must be greater or equal to 0"
            );
        }

        if (size <1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Size must be greater or equal to one"
            );
        }

        Sort sortByActName = Sort.by(Sort.Direction.ASC, "actName");

        PageRequest pageRequest = PageRequest.of(page, size, sortByActName);

        Page<Account> accounts = accountRepository.findAll(pageRequest);

        return accounts.map(accountMapper::toAccountResponse);
    }

    @Override
    public void updateTransferLimit(String actNo, AccountTransferLimitRequest request) {

        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Account has not been found"
                        )
                );

        account.setTransferLimit(request.transferLimit());

        accountRepository.save(account);

    }
}
