package co.istad.mbanking.features.cardtype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/card-types")
public class CardTypeController {

    private final CardTypeService cardTypeService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity findAllCardType(){
        return ResponseEntity.accepted().body(
                Map.of(
                        "data", cardTypeService.findAllCardType()
                )
        );
    }

    @GetMapping("{name}")
    ResponseEntity findCardTypeByName(@PathVariable String name) {
        return ResponseEntity.accepted().body(
                Map.of(
                        "data", cardTypeService.findCardTypeByName(name)
                )
        );
    }
}
