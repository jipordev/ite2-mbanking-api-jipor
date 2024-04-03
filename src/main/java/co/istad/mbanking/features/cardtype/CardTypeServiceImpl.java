package co.istad.mbanking.features.cardtype;

import co.istad.mbanking.domain.CardType;
import co.istad.mbanking.features.cardtype.dto.CardTypeResponse;
import co.istad.mbanking.mapper.CardTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTypeServiceImpl implements CardTypeService {

    private final CardTypeRepository cardRepository;
    private final CardTypeMapper cardTypeMapper;

    @Override
    public List<CardTypeResponse> findAllCardType() {

        List<CardType> cardTypes = cardRepository.findAll();

        return cardTypeMapper.toCardTypeResponseList(cardTypes);

    }

    @Override
    public CardTypeResponse findCardTypeByName(String name) {

        if (!cardRepository.existsByName(name)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Card type does not exist"
            );
        }

        CardType cardType = cardRepository.findByName(name)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Card type has not been found"
                        )
                );

        return cardTypeMapper.toCardTypeResponse(cardType);

    }
}
