package com.example.accounts_microservice.api.service.impl;

import com.example.accounts_microservice.api.documents.AccountType;
import com.example.accounts_microservice.api.repository.AccountTypeRepository;
import com.example.accounts_microservice.api.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    @Override
    public Flux<AccountType> findAll() {
        return accountTypeRepository.findAll();
    }

    @Override
    public Mono<AccountType> create(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    @Override
    public Mono<AccountType> update(String id, AccountType accountType) {
        Mono<AccountType> currentAccountTypeMono = accountTypeRepository.findById(id);
        return currentAccountTypeMono.flatMap(currentAccountType -> {
            accountType.setId(id);
            return accountTypeRepository.save(accountType);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return accountTypeRepository.deleteById(id);
    }

    @Override
    public Mono<AccountType> findByAccountTypeCode(int accountTypeCode) {
        return accountTypeRepository.findFirstByAccountTypeCode(accountTypeCode);
    }
}
