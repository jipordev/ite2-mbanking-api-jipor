package co.istad.mbanking.features.accounttype;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-types")
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @GetMapping
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
