package com.example.accounts_microservice.api.dto.account_creation;

import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import lombok.Data;
import lombok.RequiredArgsConstructor;


// CLASE USADA PARA LA CREACION DE UNA CUENTA
@Data
@RequiredArgsConstructor
public class AccountCreationRequest {
    private ClientType clientType;
    private ProductType accountType;
    private String customerId;
    private Double maintainanceComission;
    private Double moneyAmount;
    private Integer transactionsLimit;
    private Integer operationDay;
}
