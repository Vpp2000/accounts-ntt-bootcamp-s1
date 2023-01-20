package com.example.accounts_microservice.api.service;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.dto.account_creation.AccountCreationRequest;
import com.example.accounts_microservice.api.dto.BalanceResponse;
import com.example.accounts_microservice.api.dto.account_creation.CompanyAccountCreationRequest;
import com.example.accounts_microservice.api.dto.account_creation.PersonAccountCreationRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<Account> findAll();

    Mono<Account> findById(String id);

    Mono<Account> create(Account account);
    Mono<Account> update(String id, Account account);
    Mono<Void> delete(String id);
    Mono<BalanceResponse> getBalance(String accountId, String customerId);
    Mono<Account> createCompanyAccount(CompanyAccountCreationRequest companyAccountCreationRequest);
    Mono<Account> createPersonAccount(PersonAccountCreationRequest personAccountCreationRequest);
}
