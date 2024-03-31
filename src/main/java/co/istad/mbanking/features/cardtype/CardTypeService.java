package co.istad.mbanking.features.cardtype;

import co.istad.mbanking.features.cardtype.dto.CardTypeResponse;

import java.util.List;

public interface CardTypeService {

    List<CardTypeResponse> findAllCardType();

    CardTypeResponse findCardTypeByName(String name);

}
