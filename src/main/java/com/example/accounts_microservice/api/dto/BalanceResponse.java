package com.example.accounts_microservice.api.dto;

import com.example.accounts_microservice.api.enums.ProductType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceResponse {
    private ProductType accountType;
    private Double balance;
}
