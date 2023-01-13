package com.example.accounts_microservice.api.controller;

import com.example.accounts_microservice.api.documents.AccountType;
import com.example.accounts_microservice.api.service.AccountTypeService;
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
@RequestMapping("/account-type")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @GetMapping
    public Flux<AccountType> findAll(){
        return accountTypeService.findAll();
    }

    @GetMapping("/by-account-type-code/{accountTypeCode}")
    public Mono<AccountType> findByAccountTypeCode(@PathVariable("accountTypeCode") int accountTypeCode){
        return accountTypeService.findByAccountTypeCode(accountTypeCode);
    }

    @PostMapping
    public Mono<AccountType> create(@RequestBody AccountType accountType){
        return accountTypeService.create(accountType);
    }

    @PutMapping("/{id}")
    public Mono<AccountType> update(@RequestBody AccountType accountType, @PathVariable("id") String id){
        return accountTypeService.update(id, accountType);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return accountTypeService.delete(id);
    }
}
