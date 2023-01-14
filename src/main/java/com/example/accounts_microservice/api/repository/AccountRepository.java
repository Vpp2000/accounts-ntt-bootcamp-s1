package com.example.accounts_microservice.api.repository;

import com.example.accounts_microservice.api.documents.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
    Mono<Account> findFirstByCustomerId(String customerId);
}
