package co.istad.mbanking.features.user;

import co.istad.mbanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalCardId(String nationalCardId);
    boolean existsByStudentIdCard(String studentIdCard);
    boolean existsByUuid(String uuid);

}
