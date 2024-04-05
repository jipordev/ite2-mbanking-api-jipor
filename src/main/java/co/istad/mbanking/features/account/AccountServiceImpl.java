package co.istad.mbanking.features.account;

import co.istad.mbanking.domain.Account;
import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.domain.UserAccount;
import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import co.istad.mbanking.features.accounttype.AccountTypeRepository;
import co.istad.mbanking.features.user.UserRepository;
import co.istad.mbanking.features.user.UserService;
import co.istad.mbanking.features.user.dto.UserCreateRequest;
import co.istad.mbanking.features.user.dto.UserResponse;
import co.istad.mbanking.mapper.AccountMapper;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

        AccountType accountType = accountTypeRepository.findByAlias(accountCreateRequest.accountTypeAlias()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account type has not been found!"
                )
        );

        User user = userRepository.findByUuid(accountCreateRequest.userUuid()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found"
                )
        );

        Account account = accountMapper.fromAccountCreateRequest(accountCreateRequest);
        log.info("Account: {}",account.toString());
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setActNo("12345678");
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
                                "Account has not been found"
                        )
                );
        UserAccount userAccount = userAccountRepository.findByAccount(account)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Account has not been found"
                        )
                );
        User user = userAccount.getUser();
        UserResponse userResponse = new UserResponse(
                user.getUuid(),
                user.getName(),
                user.getProfileImage(),
                user.getGender(),
                user.getDob()
        );


        return new AccountResponse(
                account.getActNo(),
                account.getActName(),
                account.getAlias(),
                account.getBalance(),
                account.getTransferLimit(),
                account.getAccountType().getName(),
                userResponse
        );
    }
}
