package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.features.accounttype.dto.AccountTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {

    AccountTypeResponse toAccountTypeDetailResponse(AccountType accountType);

}
