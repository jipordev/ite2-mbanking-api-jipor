package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    void createAccount(@RequestBody AccountCreateRequest request){
        accountService.createNew(request);
    }

    @GetMapping("/{actNo}")
    AccountResponse find(@PathVariable String actNo) {
        return accountService.findByActNo(actNo);
    }
}
