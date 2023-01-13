package com.example.accounts_microservice.api.repository;

import com.example.accounts_microservice.api.documents.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

}
