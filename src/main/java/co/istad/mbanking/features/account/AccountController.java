package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountRenameRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import co.istad.mbanking.features.account.dto.AccountTransferLimitRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createAccount(@RequestBody AccountCreateRequest request){
        accountService.createNew(request);
    }

    @GetMapping("/{actNo}")
    AccountResponse find(@PathVariable String actNo){
        return accountService.findByActNo(actNo);
    }

    @GetMapping
    Page<AccountResponse> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "2") int size
    ) {
        return accountService.findAll(page, size);
    }

    @PutMapping("/{actNo}/rename")
    AccountResponse renameByActNo(@PathVariable String actNo,
                                  @Valid @RequestBody AccountRenameRequest request){
        return accountService.renameByActNo(actNo, request);
    }

    @PutMapping("/{actNo}/hide-account")
    void hideAccountByActNo(@PathVariable String actNo){
        accountService.hideAccount(actNo);
    }

    @PutMapping("/{actNo}/transfer-limit")
    void updateTransferLimit(@PathVariable String actNo, @Valid @RequestBody AccountTransferLimitRequest request){
        accountService.updateTransferLimit(actNo, request);
    }

}
