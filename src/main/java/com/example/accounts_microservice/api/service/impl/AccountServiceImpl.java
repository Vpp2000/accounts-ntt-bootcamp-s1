package com.example.accounts_microservice.api.service.impl;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.dto.AccountCreationRequest;
import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import com.example.accounts_microservice.api.repository.AccountRepository;
import com.example.accounts_microservice.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> create(Account account) {
        return accountRepository.save(account);
    }

    public Mono<Account> createAccordingRequirements(AccountCreationRequest accountCreationRequest){
        Mono<Account> accountMono = accountRepository.findFirstByCustomerId(accountCreationRequest.getCustomerId());
        return accountMono.flatMap(account -> saveCustomerAccount(account, accountCreationRequest)).switchIfEmpty(saveFirstCustomerAccount(accountCreationRequest));
    }

    private Mono<Account> saveCustomerAccount(Account account, AccountCreationRequest accountCreationRequest){
        Account newAccount = new Account();

        if(accountCreationRequest.getClientType().equals(ClientType.PERSON)){
            if(account.getAccountType().equals(ProductType.PLAZO_FIJO)){
                newAccount = Account.plazoFijoAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            }
            else {
                return Mono.empty();
            }
        } else if(accountCreationRequest.getClientType().equals(ClientType.COMPANY)){
            if(accountCreationRequest.getAccountType().equals(ProductType.C_CORRIENTE)){
                newAccount = Account.cuentaCorrienteAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            } else {
                return Mono.empty();
            }
        } else {
            return Mono.empty();
        }
    }

    private Mono<Account> saveFirstCustomerAccount(AccountCreationRequest accountCreationRequest){
        Account newAccount;

        if(accountCreationRequest.getClientType().equals(ClientType.PERSON)){
            if(accountCreationRequest.getAccountType().equals(ProductType.C_CORRIENTE)){
                newAccount = Account.cuentaCorrienteAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            } else if (accountCreationRequest.getAccountType().equals(ProductType.AHORRO)) {
                newAccount = Account.ahorroAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            } else if (accountCreationRequest.getAccountType().equals(ProductType.PLAZO_FIJO)){
                newAccount = Account.plazoFijoAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            } else {
                return Mono.empty();
            }
        } else if(accountCreationRequest.getClientType().equals(ClientType.COMPANY)){
            if(accountCreationRequest.getAccountType().equals(ProductType.C_CORRIENTE)){
                newAccount = Account.cuentaCorrienteAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            } else {
                return Mono.empty();
            }
        } else {
            return Mono.empty();
        }
    }

    /*

    *public Mono<Account> create(AccountCreateRequest accountRequest) {
        Mono<Account> accountMono = accountRepository.findFirstByCustomerId(accountRequest.customerId)

        accountMono.map(account -> {
            if(account.getType().equals("PLAZO_FIJO)){

            } else {
            }
        }).defaultIfEmpty(account -> accountRepository.save(account))

        return accountRepository.save(account);
    }

    *
    *
    * */

    @Override
    public Mono<Account> update(String id, Account account) {
        Mono<Account> currentAccountMono = accountRepository.findById(id);
        return currentAccountMono.flatMap(currentAccount -> {
            account.setId(id);
            return accountRepository.save(account);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return accountRepository.deleteById(id);
    }

}
