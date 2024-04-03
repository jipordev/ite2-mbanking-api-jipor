package co.istad.mbanking.mapper;

import co.istad.mbanking.domain.CardType;
import co.istad.mbanking.features.cardtype.dto.CardTypeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardTypeMapper {

    List<CardTypeResponse> toCardTypeResponseList(List<CardType> cardType);
    CardTypeResponse toCardTypeResponse(CardType cardType);



}
