package com.example.accounts_microservice.api.controller;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.dto.account_creation.AccountCreationRequest;
import com.example.accounts_microservice.api.dto.BalanceRequest;
import com.example.accounts_microservice.api.dto.BalanceResponse;
import com.example.accounts_microservice.api.dto.account_creation.CompanyAccountCreationRequest;
import com.example.accounts_microservice.api.dto.account_creation.PersonAccountCreationRequest;
import com.example.accounts_microservice.api.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public Flux<Account> findAll(){
        return accountService.findAll();
    }

    @GetMapping("/{accountId}/balance")
    public Mono<BalanceResponse> getAccountBalance(@PathVariable("accountId") String accountId, @RequestBody BalanceRequest balanceRequest){
        return accountService.getBalance(accountId, balanceRequest.getCustomerId());
    }

    @PostMapping
    public Mono<Account> create(@RequestBody Account account){
        return accountService.create(account);
    }

    @PostMapping("/company")
    public Mono<ResponseEntity<Account>> createForCompany(@Valid @RequestBody CompanyAccountCreationRequest companyAccountCreationRequest){
        return accountService.createCompanyAccount(companyAccountCreationRequest).map(account -> ResponseEntity.status(201).body(account));
    }

    @PostMapping("/person")
    public Mono<ResponseEntity<Account>> createForPerson(@Valid @RequestBody PersonAccountCreationRequest personAccountCreationRequest){
        return accountService.createPersonAccount(personAccountCreationRequest).map(account -> ResponseEntity.status(201).body(account));
    }

    @PutMapping("/{id}")
    public Mono<Account> update(@RequestBody Account account, @PathVariable("id") String id){
        return accountService.update(id, account);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id){
        return accountService.findById(id).flatMap(p -> accountService.delete(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
