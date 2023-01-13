package com.example.accounts_microservice.api.repository;

import com.example.accounts_microservice.api.documents.AccountType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountTypeRepository extends ReactiveMongoRepository<AccountType, String> {
    Mono<AccountType> findFirstByAccountTypeCode(int accountTypeCode);
}
