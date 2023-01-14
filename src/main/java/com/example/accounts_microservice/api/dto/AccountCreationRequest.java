package com.example.accounts_microservice.api.dto;

import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class AccountCreationRequest {
    private ClientType clientType;
    private ProductType accountType;
    private String customerId;
    private Double maintainanceComission;
    private Double moneyAmount;
    private Integer transactionsLimit;
    private Date operationDate;
}
