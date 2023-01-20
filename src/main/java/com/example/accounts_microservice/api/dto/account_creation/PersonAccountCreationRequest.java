package com.example.accounts_microservice.api.dto.account_creation;


import com.example.accounts_microservice.api.enums.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonAccountCreationRequest {
    @NotNull
    private String customerId;
    @NotNull
    private ProductType accountType;
    @NotNull
    private Double moneyAmount;
    private Integer operationDay;

}
