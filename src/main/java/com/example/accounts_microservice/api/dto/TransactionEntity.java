package com.example.accounts_microservice.api.dto;

import com.example.accounts_microservice.api.enums.ProductType;
import com.example.accounts_microservice.api.enums.TransactionType;
import java.util.Date;
import lombok.Data;

// CLASE PARA RECIBIR LA TRANSACCION CREADA POR EL MICROSERVICIO DE TRANSACCIONES
@Data
public class TransactionEntity {
    private String id;
    private ProductType productType;  // AHORRO, C_CORRIENTE, PLAZO_FIJO, CRE_PERSONAL, CRED_EMPRESARIAL, TAR_CRED_PERSONAL, TAR_CRED_EMPRESARIAL
    private String productId;
    private String customerId;
    private TransactionType transactionType;  // DEPOSITO, RETIRO, PAGO, CONSUMO
    private Double amount;
    private Date transactionDate;

}
