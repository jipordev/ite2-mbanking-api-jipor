package co.istad.mbanking.init;

import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.CardType;
import co.istad.mbanking.domain.Role;
import co.istad.mbanking.features.accounttype.AccountTypeRepository;
import co.istad.mbanking.features.cardtype.CardTypeRepository;
import co.istad.mbanking.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final CardTypeRepository cardTypeRepository;

    @PostConstruct
    void initRole() {

        // Auto generate role (USER, CUSTOMER, STAFF, ADMIN)
        if (roleRepository.count() < 1) {
            Role user = new Role();
            user.setName("USER");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role staff = new Role();
            staff.setName("STAFF");

            Role admin = new Role();
            admin.setName("ADMIN");

            roleRepository.saveAll(
                    List.of(user, customer, staff, admin)
            );
        }

    }

    @PostConstruct
    void initAccountType(){
        if (accountTypeRepository.count() < 1) {
            AccountType savingAccountType = new AccountType();
            savingAccountType.setName("Saving Account");
            savingAccountType.setAlias("saving-account");
            savingAccountType.setIsDeleted(false);
            savingAccountType.setDescription("A saving account is a deposit...");
//            accountTypeRepository.save(savingAccountType);

            AccountType payrollAccountType = new AccountType();
            payrollAccountType.setName("Payroll Account");
            payrollAccountType.setAlias("payroll-account");
            payrollAccountType.setIsDeleted(false);
            payrollAccountType.setDescription("A payroll account is a type of account...");
//            accountTypeRepository.save(payrollAccountType);

            AccountType cardAccountType = new AccountType();
            cardAccountType.setName("Card Account");
            cardAccountType.setAlias("card-account");
            cardAccountType.setIsDeleted(false);
            cardAccountType.setDescription("A payroll account is a type of account...");
//            accountTypeRepository.save(cardAccountType);

            accountTypeRepository.saveAll(
                    List.of(savingAccountType, payrollAccountType,
                            cardAccountType)
            );

        }
    }

    @PostConstruct
    void initCardType(){
        if (cardTypeRepository.count() < 1){
            CardType masterCard = new CardType();
            masterCard.setName("Master Card");
            masterCard.setIsDeleted(false);

            CardType visaCard = new CardType();
            visaCard.setName("Visa Card");
            visaCard.setIsDeleted(false);

            cardTypeRepository.save(masterCard);
            cardTypeRepository.save(visaCard);
        }
    }

}

