package com.example.accounts_microservice.api.dto;

import com.example.accounts_microservice.api.enums.ProductType;
import com.example.accounts_microservice.api.enums.TransactionType;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
public class TransactionCreateRequest {
    private ProductType productType;  // AHORRO, C_CORRIENTE, PLAZO_FIJO, CRE_PERSONAL, CRED_EMPRESARIAL, TAR_CRED_PERSONAL, TAR_CRED_EMPRESARIAL
    private String productId;
    private String customerId;
    private TransactionType transactionType;  // DEPOSITO, RETIRO, PAGO, CONSUMO
    private Double amount;
}
