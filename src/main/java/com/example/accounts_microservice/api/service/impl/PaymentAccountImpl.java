package com.example.accounts_microservice.api.service.impl;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.dto.TransactionCreateRequest;
import com.example.accounts_microservice.api.dto.TransactionEntity;
import com.example.accounts_microservice.api.enums.TransactionType;
import com.example.accounts_microservice.api.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentAccountImpl {
    private static final Logger logger_console = LoggerFactory.getLogger("root");
    private final AccountRepository accountRepository;

    @Transactional
    public Mono<Account> performWithdraw(String accountId, Double amount, String customerId){
        Mono<Account> accountMono = accountRepository.findById(accountId);

        return accountMono.flatMap(account -> {
            if(!account.getCustomerId().equals(customerId)){
                return Mono.error(new Exception(String.format("Account %s and customerId %s are not associated", accountId, customerId)));
            }

            Account newAccount;

            try {
                account.withDrawMoney(amount);
            } catch (Exception e) {
                return Mono.error(e);
            }

            logger_console.info("Account: {}", account.toString());

            newAccount = account;
            return accountRepository.save(newAccount).flatMap(updatedAccount -> {
                TransactionCreateRequest transactionCreateRequest = TransactionCreateRequest.builder()
                        .transactionType(TransactionType.RETIRO)
                        .productId(updatedAccount.getId())
                        .customerId(updatedAccount.getCustomerId())
                        .amount(amount)
                        .productType(updatedAccount.getAccountType())
                        .build();

                return sendPayloadToTransactions(transactionCreateRequest).map(t -> {
                    return updatedAccount;
                });

            });
        });
    }

    @Transactional
    public Mono<Account> performDeposit(String accountId, Double amount, String customerId){
        Mono<Account> accountMono = accountRepository.findById(accountId);
        return accountMono.flatMap(account -> {
            if(!account.getCustomerId().equals(customerId)){
                return Mono.error(new Exception(String.format("Account %s and customerId %s are not associated", accountId, customerId)));
            }

            Account newAccount;

            try {
                account.depositMoney(amount);
            } catch (Exception e) {
                return Mono.error(e);
            }

            logger_console.info("Account: {}", account.toString());

            newAccount = account;

            return accountRepository.save(newAccount).flatMap(updatedAccount -> {
                TransactionCreateRequest transactionCreateRequest = TransactionCreateRequest.builder()
                        .transactionType(TransactionType.DEPOSITO)
                        .productId(updatedAccount.getId())
                        .customerId(updatedAccount.getCustomerId())
                        .amount(amount)
                        .productType(updatedAccount.getAccountType())
                        .build();

                return sendPayloadToTransactions(transactionCreateRequest).map(t -> {
                    return updatedAccount;
                });

            });
        });
    }


    private Mono<TransactionEntity> sendPayloadToTransactions(TransactionCreateRequest transactionCreateRequest){
        WebClient webClient = WebClient.create("http://localhost:8080");

        return  webClient.post()
                .uri("/transaction")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(transactionCreateRequest), TransactionCreateRequest.class)
                .retrieve().bodyToMono(TransactionEntity.class);

    }

}
