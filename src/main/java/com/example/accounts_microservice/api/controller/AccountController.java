package com.example.accounts_microservice.api.controller;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.documents.AccountType;
import com.example.accounts_microservice.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @PostMapping
    public Mono<Account> create(@RequestBody Account account){
        return accountService.create(account);
    }

    @PutMapping("/{id}")
    public Mono<Account> update(@RequestBody Account account, @PathVariable("id") String id){
        return accountService.update(id, account);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return accountService.delete(id);
    }
}
