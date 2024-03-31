package co.istad.mbanking.features.cardtype;

import co.istad.mbanking.features.cardtype.dto.CardTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTypeServiceImpl implements CardTypeService {

    private final CardTypeRepository cardRepository;

    @Override
    public List<CardTypeResponse> findAllCardType() {
        return cardRepository.findAll().stream()
                .map(cardType -> new CardTypeResponse(
                        cardType.getName(),
                        cardType.getIsDeleted()
                )).toList();
    }

    @Override
    public CardTypeResponse findCardTypeByName(String name) {

        if (!cardRepository.existsByName(name)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Card type does not exist"
            );
        }

        return cardRepository.findAll().stream()
                .filter(cardType -> cardType.getName().equals(name))
                .map(cardType -> new CardTypeResponse(
                        cardType.getName(),
                        cardType.getIsDeleted()
                ))
                .findFirst().orElseThrow();

    }
}
