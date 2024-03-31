package co.istad.mbanking.features.accounttype;

import co.istad.mbanking.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    Boolean existsByAlias(String alias);

}
