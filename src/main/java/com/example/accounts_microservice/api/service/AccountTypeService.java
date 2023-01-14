package com.example.accounts_microservice.api.service;

import com.example.accounts_microservice.api.documents.AccountTypeDoc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountTypeService {
    Flux<AccountTypeDoc> findAll();
    Mono<AccountTypeDoc> create(AccountTypeDoc accountTypeDoc);

    Mono<AccountTypeDoc> update(String id, AccountTypeDoc accountTypeDoc);
    Mono<Void> delete(String id);

    Mono<AccountTypeDoc> findByAccountTypeCode(int accountTypeCode);
}
