package co.istad.mbanking.features.accounttype;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.features.accounttype.dto.AccountTypeResponse;
import co.istad.mbanking.mapper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService{

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public List<AccountTypeResponse> findAccountTypeList() {
         List<AccountType> accountTypes = accountTypeRepository.findAll();
         return accountTypeMapper.toAccountTypeResponseList(accountTypes);
    }

    @Override
    public AccountTypeResponse findAccountTypeByAlias(String alias) {
        if (!accountTypeRepository.existsByAlias(alias)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account type does not exist"
            );
        }
        AccountType accountType = accountTypeRepository.findByAlias(alias).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account type has not been found"
                )
        );
        return accountTypeMapper.toAccountTypeResponse(accountType);
    }
}
