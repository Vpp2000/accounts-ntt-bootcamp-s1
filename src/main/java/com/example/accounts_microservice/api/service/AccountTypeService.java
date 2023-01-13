package com.example.accounts_microservice.api.service;

import com.example.accounts_microservice.api.documents.AccountType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountTypeService {
    Flux<AccountType> findAll();
    Mono<AccountType> create(AccountType accountType);

    Mono<AccountType> update(String id, AccountType accountType);
    Mono<Void> delete(String id);

    Mono<AccountType> findByAccountTypeCode(int accountTypeCode);
}
