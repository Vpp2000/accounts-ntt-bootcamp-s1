package com.example.accounts_microservice.api.dto;

import com.example.accounts_microservice.api.enums.ProductType;
import lombok.Builder;
import lombok.Data;

// CLASE USADA PARA LA RESPUESTA DE CONSULTA DE SALDO DE UNA CUENTA
@Data
@Builder
public class BalanceResponse {
    private ProductType accountType;
    private Double balance;
}
