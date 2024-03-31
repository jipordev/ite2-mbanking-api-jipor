package co.istad.mbanking.features.accounttype;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-types")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<?> findAllAccountType(){
        return ResponseEntity.accepted().body(
                Map.of(
                        "data", accountTypeService.findAccountTypeList()
                )
        );
    }

    @GetMapping("/{alias}")
    ResponseEntity<?> findAccountTypeByAlias(@PathVariable String alias){
        return ResponseEntity.accepted().body(
                Map.of(
                        "data", accountTypeService.findAccountTypeByAlias(alias)
                )
        );
    }
}
