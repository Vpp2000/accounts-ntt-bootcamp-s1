package com.example.accounts_microservice.api.controller;

import com.example.accounts_microservice.api.documents.Account;
import com.example.accounts_microservice.api.dto.OperationRequest;
import com.example.accounts_microservice.api.service.impl.PaymentAccountImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


// CONTROLADOR PARA EL MANEJO DE PAGOS Y DEPOSITOS
@RestController
@RequiredArgsConstructor
public class PaymentAccountController {

    private final PaymentAccountImpl paymentAccount;

    @PostMapping("/accounts/{accountId}/deposit")
    public Mono<Account> performDeposit(@PathVariable("accountId") String accountId, @RequestBody OperationRequest operationRequest){
        return paymentAccount.performDeposit(accountId, operationRequest.getAmount(), operationRequest.getCustomerId());
    }

    @PostMapping("/accounts/{accountId}/withdraw")
    public Mono<Account> performWithdraw(@PathVariable("accountId") String accountId, @RequestBody OperationRequest operationRequest){
        return paymentAccount.performWithdraw(accountId, operationRequest.getAmount(), operationRequest.getCustomerId());
    }
}
