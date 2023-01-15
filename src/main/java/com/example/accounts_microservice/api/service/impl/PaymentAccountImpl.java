package com.example.accounts_microservice.api.service.impl;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentAccountImpl {
    private static final Logger logger_console = LoggerFactory.getLogger("root");
    private final AccountRepository accountRepository;

    @Transactional
    public Mono<Account> performWithdraw(String accountId, Double amount){
        Mono<Account> accountMono = accountRepository.findById(accountId);

        return accountMono.flatMap(account -> {
            try {
                account.withDrawMoney(amount);
                account.incrementTransactionQuantity();
            } catch (Exception e) {
                throw Exceptions.propagate(e);
            }

            return accountMono;
        });
    }

    public Mono<Account> performDeposit(String accountId, Double amount){
        Mono<Account> accountMono = accountRepository.findById(accountId);
        return accountMono.flatMap(account -> {
            Account newAccount;

            try {
                account.depositMoney(amount);
            } catch (Exception e) {
                return Mono.error(e);
            }

            logger_console.info("Account: {}", account.toString());

            newAccount = account;
            return accountRepository.save(newAccount);
        });

    }

}
