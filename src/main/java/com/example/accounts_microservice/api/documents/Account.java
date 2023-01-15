package com.example.accounts_microservice.api.documents;

import com.example.accounts_microservice.api.dto.AccountCreationRequest;
import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


// represents passive products
@Document("accounts")
@Data
@NoArgsConstructor
public class Account {
    @Id
    private String id;

    private ClientType clientType;
    private ProductType accountType;
    private UUID accountNumber;
    private String customerId;
    private Double maintainanceComission;
    private Double moneyAmount;
    private Integer transactionsLimit;
    private Integer transactionsQuantity;
    private Date operationDate;


    public static Account plazoFijoAccountFromAccountRequest(AccountCreationRequest accountCreationRequest){
        Account newAccount = new Account();
        newAccount.setClientType(accountCreationRequest.getClientType());
        newAccount.setAccountType(ProductType.PLAZO_FIJO);
        newAccount.setAccountNumber(UUID.randomUUID());
        newAccount.setCustomerId(accountCreationRequest.getCustomerId());
        newAccount.setMaintainanceComission(0.0);
        newAccount.setMoneyAmount(0.0);
        newAccount.setTransactionsLimit(1);
        newAccount.setTransactionsQuantity(0);
        newAccount.setOperationDate(accountCreationRequest.getOperationDate());

        return newAccount;
    }

    public static Account ahorroAccountFromAccountRequest(AccountCreationRequest accountCreationRequest){
        Account newAccount = new Account();
        newAccount.setClientType(accountCreationRequest.getClientType());
        newAccount.setAccountType(ProductType.AHORRO);
        newAccount.setAccountNumber(UUID.randomUUID());
        newAccount.setCustomerId(accountCreationRequest.getCustomerId());
        newAccount.setMaintainanceComission(0.0);
        newAccount.setMoneyAmount(0.0);
        newAccount.setTransactionsLimit(accountCreationRequest.getTransactionsLimit());
        newAccount.setTransactionsQuantity(0);
        newAccount.setOperationDate(null);

        return newAccount;
    }

    public static Account cuentaCorrienteAccountFromAccountRequest(AccountCreationRequest accountCreationRequest){
        Account newAccount = new Account();
        newAccount.setClientType(accountCreationRequest.getClientType());
        newAccount.setAccountType(ProductType.C_CORRIENTE);
        newAccount.setAccountNumber(UUID.randomUUID());
        newAccount.setCustomerId(accountCreationRequest.getCustomerId());
        newAccount.setMaintainanceComission(accountCreationRequest.getMaintainanceComission());
        newAccount.setMoneyAmount(0.0);
        newAccount.setTransactionsLimit(null);
        newAccount.setTransactionsQuantity(0);
        newAccount.setOperationDate(null);

        return newAccount;
    }
}
