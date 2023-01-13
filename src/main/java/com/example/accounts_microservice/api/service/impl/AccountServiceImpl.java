package com.example.accounts_microservice.api.service.impl;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.documents.AccountType;
import com.example.accounts_microservice.api.repository.AccountRepository;
import com.example.accounts_microservice.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> update(String id, Account account) {
        Mono<Account> currentAccountMono = accountRepository.findById(id);
        return currentAccountMono.flatMap(currentAccount -> {
            account.setId(id);
            return accountRepository.save(account);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return accountRepository.deleteById(id);
    }

}
