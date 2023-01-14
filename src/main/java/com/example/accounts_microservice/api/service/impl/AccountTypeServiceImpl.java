package com.example.accounts_microservice.api.service.impl;

import com.example.accounts_microservice.api.documents.AccountTypeDoc;
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
    public Flux<AccountTypeDoc> findAll() {
        return accountTypeRepository.findAll();
    }

    @Override
    public Mono<AccountTypeDoc> create(AccountTypeDoc accountTypeDoc) {
        return accountTypeRepository.save(accountTypeDoc);
    }

    @Override
    public Mono<AccountTypeDoc> update(String id, AccountTypeDoc accountTypeDoc) {
        Mono<AccountTypeDoc> currentAccountTypeMono = accountTypeRepository.findById(id);
        return currentAccountTypeMono.flatMap(currentAccountType -> {
            accountTypeDoc.setId(id);
            return accountTypeRepository.save(accountTypeDoc);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return accountTypeRepository.deleteById(id);
    }

    @Override
    public Mono<AccountTypeDoc> findByAccountTypeCode(int accountTypeCode) {
        return accountTypeRepository.findFirstByAccountTypeCode(accountTypeCode);
    }
}
