package co.istad.mbanking.features.cardtype;

import co.istad.mbanking.domain.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRepository extends JpaRepository<CardType, Long> {

    Boolean existsByName(String name);

}
