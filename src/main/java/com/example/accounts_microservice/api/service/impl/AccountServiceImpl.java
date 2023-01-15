package com.example.accounts_microservice.api.service.impl;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.dto.AccountCreationRequest;
import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import com.example.accounts_microservice.api.repository.AccountRepository;
import com.example.accounts_microservice.api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Logger logger_console = LoggerFactory.getLogger("root");

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
        return accountMono.hasElement().flatMap(flag -> {
            if(flag == true){
                return accountMono.flatMap(account -> saveCustomerAccount(account, accountCreationRequest));
            }
            else return saveFirstCustomerAccount(accountCreationRequest);
        });
    }

    private Mono<Account> saveCustomerAccount(Account account, AccountCreationRequest accountCreationRequest){
        logger_console.info("Creating account for customer with existing account of type {}", account.getAccountType());

        Account newAccount;

        if(accountCreationRequest.getClientType().equals(ClientType.PERSON)){
            if(accountCreationRequest.getAccountType().equals(ProductType.PLAZO_FIJO)){
                newAccount = Account.plazoFijoAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            }
            else {
                logger_console.info("Account creation failed because is PERSON and is not PLAZO FIJO");
                return Mono.empty();
            }
        } else if(accountCreationRequest.getClientType().equals(ClientType.COMPANY)){
            if(accountCreationRequest.getAccountType().equals(ProductType.C_CORRIENTE)){
                newAccount = Account.cuentaCorrienteAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            } else {
                logger_console.info("Account creation failed because is COMPANY and is not C_CORRIENTE");
                return Mono.empty();
            }
        } else {
            return Mono.empty();
        }
    }

    private Mono<Account> saveFirstCustomerAccount(AccountCreationRequest accountCreationRequest){
        logger_console.info("Creating first account for customer");

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
                logger_console.info("Account creation failed because is PERSON and is not a valid account type");
                return Mono.empty();
            }
        } else if(accountCreationRequest.getClientType().equals(ClientType.COMPANY)){
            if(accountCreationRequest.getAccountType().equals(ProductType.C_CORRIENTE)){
                newAccount = Account.cuentaCorrienteAccountFromAccountRequest(accountCreationRequest);
                return accountRepository.save(newAccount);
            } else {
                logger_console.info("Account creation failed because is COMPANY and is not a valid account type");
                return Mono.empty();
            }
        } else {
            logger_console.info("Account creation failed because is not PERSON and neither COMPANY");
            return Mono.empty();
        }
    }

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
