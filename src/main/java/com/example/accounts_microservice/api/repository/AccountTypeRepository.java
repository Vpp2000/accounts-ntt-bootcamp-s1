package com.example.accounts_microservice.api.repository;

import com.example.accounts_microservice.api.documents.AccountTypeDoc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountTypeRepository extends ReactiveMongoRepository<AccountTypeDoc, String> {
    Mono<AccountTypeDoc> findFirstByAccountTypeCode(int accountTypeCode);
}
