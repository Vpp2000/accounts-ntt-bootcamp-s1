package com.example.accounts_microservice.api.dto;

import lombok.Data;

// CLASE USADA PARA EL PAYLOAD DE CONSULTA DE SALDO DE UNA CUENTA
@Data
public class BalanceRequest {
    private String customerId;
}
