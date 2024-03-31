package co.istad.mbanking.features.accounttype;

import co.istad.mbanking.features.accounttype.dto.AccountTypeResponse;
import co.istad.mbanking.mapper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService{

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public List<AccountTypeResponse> findAccountTypeList() {
        return accountTypeRepository.findAll()
                .stream().map(
                        accountType -> new AccountTypeResponse(
                                accountType.getAlias(),
                                accountType.getName(),
                                accountType.getDescription(),
                                accountType.getIsDeleted()
                        )
                ).collect(Collectors.toList());
    }

    @Override
    public AccountTypeResponse findAccountTypeByAlias(String alias) {
        if (!accountTypeRepository.existsByAlias(alias)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account type does not exist"
            );
        }
        return accountTypeRepository.findAll()
                .stream()
                .map(accountType -> new AccountTypeResponse(
                        accountType.getAlias(),
                        accountType.getName(),
                        accountType.getDescription(),
                        accountType.getIsDeleted()
                ))
                .findFirst().orElseThrow();
    }
}
