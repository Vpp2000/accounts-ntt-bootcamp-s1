package com.example.accounts_microservice.api.service;

import com.example.accounts_microservice.api.documents.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> findAll();
    Mono<Account> create(Account account);

    Mono<Account> update(String id, Account account);
    Mono<Void> delete(String id);
}
