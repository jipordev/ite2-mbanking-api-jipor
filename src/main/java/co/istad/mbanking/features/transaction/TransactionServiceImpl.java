package co.istad.mbanking.features.transaction;

import co.istad.mbanking.domain.Account;
import co.istad.mbanking.domain.Transaction;
import co.istad.mbanking.features.account.AccountRepository;
import co.istad.mbanking.features.transaction.dto.TransactionCreateRequest;
import co.istad.mbanking.features.transaction.dto.TransactionResponse;
import co.istad.mbanking.mapper.AccountMapper;
import co.istad.mbanking.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public TransactionResponse transfer(TransactionCreateRequest request) {

        Account owner = accountRepository.findByActNo(request.accountOwner())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Account has not been found"
                        )
                );

        Account transferReceiver = accountRepository.findByActNo(request.accountTransferReceiver())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Account has not been found"
                        )
                );

        if (owner.getActNo().equals(transferReceiver.getActNo())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You can't transfer to your own account"
            );
        }

        // check transfer amount (amount <= balance)
        if (owner.getBalance().doubleValue() < request.amount().doubleValue()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient balance"
            );
        }
        // check amount with transfer limit
        if (request.amount().doubleValue() >= owner.getTransferLimit().doubleValue()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Your amount is over transfer limit"
            );
        }

        // dork luy jenh pi owner account
        owner.setBalance(owner.getBalance().subtract(request.amount()));
        accountRepository.save(owner);
        // banhjol luy tv account
        transferReceiver.setBalance(transferReceiver.getBalance().add(request.amount()));
        accountRepository.save(transferReceiver);

        Transaction transaction = transactionMapper.fromTransactionCreateRequest(request);
        transaction.setOwner(owner);
        transaction.setTransferReceiver(transferReceiver);
        transaction.setTransactionType("TRANSFER");
        transaction.setTransactionAt(LocalDateTime.now());
        transaction.setStatus(true);
        transaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(transaction);

    }

    @Override
    public Page<TransactionResponse> findAll(int page, int size, Sort sort, String transactionType) {
        if (page < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page number must be greater or equal to 0");
        }

        if (size < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size must be greater or equal to one");
        }

        // Construct the predicate for filtering based on transactionType
        Specification<Transaction> spec = Specification.where(null);
        if (transactionType != null && !transactionType.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("transactionType"), transactionType));
        }

        // Construct Pageable with sorting
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch page of transactions based on criteria
        Page<Transaction> transactionPage = transactionRepository.findAll(spec, pageable);

        // Map Transaction entities to TransactionResponse objects
        return transactionPage.map(transactionMapper::toTransactionResponse);
    }

}