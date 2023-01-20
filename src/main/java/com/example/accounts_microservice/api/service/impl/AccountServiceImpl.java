package com.example.accounts_microservice.api.service.impl;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.dto.BalanceResponse;
import com.example.accounts_microservice.api.dto.account_creation.CompanyAccountCreationRequest;
import com.example.accounts_microservice.api.dto.account_creation.PersonAccountCreationRequest;
import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import com.example.accounts_microservice.api.repository.AccountRepository;
import com.example.accounts_microservice.api.service.AccountService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Logger logger_console = LoggerFactory.getLogger("root");
    private final AccountRepository accountRepository;

    @Value("${accounts.minimum_money}")
    private Double minimumMoney;

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id);
    }

    @Override
    public Mono<Account> create(Account account){
        return accountRepository.save(account);
    }
    @Override
    public Mono<Account> update(String id, Account account) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id) {
        return null;
    }

    @Override
    public Mono<BalanceResponse> getBalance(String accountId, String customerId) {
        Mono<Account> accountMono = accountRepository.findById(accountId);

        return accountMono.flatMap(account -> {
            if(!account.getCustomerId().equals(customerId)){
                return Mono.error(new Exception(String.format("Account %s and customerId %s are not associated", accountId, customerId)));
            }

            BalanceResponse balanceResponse = BalanceResponse.builder().balance(account.getMoneyAmount()).accountType(account.getAccountType()).build();
            return Mono.just(balanceResponse);
        });
    }

    public Mono<Account> createCompanyAccount(CompanyAccountCreationRequest companyAccountCreationRequest) {
        if(companyAccountCreationRequest.getHolders().isEmpty()) {
            return Mono.error(new Exception("Holders cannot be empty"));
        }

        if(companyAccountCreationRequest.getMoneyAmount() < minimumMoney){
            return Mono.error(new Exception(String.format("Minimum amount of %f is required", minimumMoney)));
        }

        Account newAccount = new Account();
        newAccount.setClientType(ClientType.COMPANY);
        newAccount.setAccountType(ProductType.C_CORRIENTE);
        newAccount.setAccountNumber(UUID.randomUUID());
        newAccount.setCustomerId(companyAccountCreationRequest.getCustomerId());
        newAccount.setMoneyAmount(companyAccountCreationRequest.getMoneyAmount());
        newAccount.setOperationDay(null);
        newAccount.setSigners(companyAccountCreationRequest.getSigners());
        newAccount.setHolders(companyAccountCreationRequest.getHolders());

        return accountRepository.save(newAccount);
    }

    public Mono<Account> createPersonAccount(PersonAccountCreationRequest personAccountCreationRequest){
        if(personAccountCreationRequest.getMoneyAmount() < minimumMoney){
            return Mono.error(new Exception(String.format("Minimum amount of %f is required", minimumMoney)));
        }

        Mono<Account> accountMono = accountRepository.findFirstByCustomerId(personAccountCreationRequest.getCustomerId());

        return accountMono.hasElement().flatMap(hasAccount -> {
            if(hasAccount){
                return accountMono.flatMap(account -> saveCustomerAccount(account, personAccountCreationRequest));
            }
            else return saveFirstCustomerAccount(personAccountCreationRequest);
        });

    }

    private Mono<Account> saveCustomerAccount(Account existingAccount, PersonAccountCreationRequest personAccountCreationRequest){
        logger_console.info("Creating account for customer {} with existing account of type {}", existingAccount.getClientType(), existingAccount.getAccountType());

        Account newAccount = new Account();

        if(existingAccount.getAccountType().equals(ProductType.PLAZO_FIJO) && personAccountCreationRequest.getAccountType().equals(ProductType.PLAZO_FIJO)){
            newAccount.setClientType(ClientType.PERSON);
            newAccount.setAccountType(personAccountCreationRequest.getAccountType());
            newAccount.setAccountNumber(UUID.randomUUID());
            newAccount.setCustomerId(personAccountCreationRequest.getCustomerId());
            newAccount.setMoneyAmount(personAccountCreationRequest.getMoneyAmount());
            newAccount.setOperationDay(personAccountCreationRequest.getOperationDay());

            return accountRepository.save(newAccount);
        }
        else {
                logger_console.info("Account creation failed because this person already hast an account");
                return Mono.error(new Exception("Account creation failed because this person already hast an account"));
        }
    }

    // ESTO OCURRE SI SE TIENE QUE EL USUARIO NO POSEÍA NINGUNA CUENTA Y SE HACEN LAS VERIFICACIONES PERTINENTES
    // PARA PODER CUMPLIR CON LOS REQUERIMIENTOS
    private Mono<Account> saveFirstCustomerAccount(PersonAccountCreationRequest personAccountCreationRequest){
        Account newAccount = new Account();

        newAccount.setClientType(ClientType.PERSON);
        newAccount.setAccountType(personAccountCreationRequest.getAccountType());
        newAccount.setAccountNumber(UUID.randomUUID());
        newAccount.setCustomerId(personAccountCreationRequest.getCustomerId());
        newAccount.setMoneyAmount(personAccountCreationRequest.getMoneyAmount());
        newAccount.setOperationDay(personAccountCreationRequest.getOperationDay());

        return accountRepository.save(newAccount);

    }


}
