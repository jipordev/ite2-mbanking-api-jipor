package co.istad.mbanking.features.account;

import co.istad.mbanking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByActNo(String actNo);

    Boolean existsByActNo(String actNo);

    @Modifying
    @Query("""
        UPDATE Account as a
        SET a.isHidden = TRUE
        WHERE a.actNo = ?1
    """)
    void hideAccountByActNo(String actNo);

}
