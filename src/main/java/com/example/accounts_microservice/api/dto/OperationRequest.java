package com.example.accounts_microservice.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;


// CLASE UTILIZADA AL MOMENTO DE OCURRIR UN DEPOSITO O UN RETIRO DE DINERO
@Data
@RequiredArgsConstructor
public class OperationRequest {
    private Double amount;
    private String customerId;
}
