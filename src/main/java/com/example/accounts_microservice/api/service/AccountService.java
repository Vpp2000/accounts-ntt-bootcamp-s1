package com.example.accounts_microservice.api.service;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.dto.AccountCreationRequest;
import com.example.accounts_microservice.api.dto.BalanceResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> findAll();

    Mono<Account> findById(String id);

    Mono<Account> create(AccountCreationRequest accountCreationRequest);
    Mono<Account> update(String id, Account account);
    Mono<Void> delete(String id);
    Mono<BalanceResponse> getBalance(String accountId, String customerId);
}
