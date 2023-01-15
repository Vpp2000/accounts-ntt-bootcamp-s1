package com.example.accounts_microservice.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OperationRequest {
    private Double amount;
}
