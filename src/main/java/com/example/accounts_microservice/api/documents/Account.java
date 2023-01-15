package com.example.accounts_microservice.api.documents;

import com.example.accounts_microservice.api.dto.AccountCreationRequest;
import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


// represents passive products
@Document("accounts")
@NoArgsConstructor
@Data
public class Account {

    private static final Logger logger_console = LoggerFactory.getLogger("root");

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
    private Integer operationDay;

    public void incrementTransactionQuantity(){
        this.transactionsQuantity += 1;
    }

    public void depositMoney(Double money) throws Exception {
        logger_console.info("deposit {} soles", money);

        switch (accountType) {
            case AHORRO:
                if(transactionsLimit == transactionsQuantity){
                    throw new Exception(String.format("Your limit is %d and you've done %d until now", transactionsLimit, transactionsQuantity) );
                }

                this.moneyAmount += money;
                this.incrementTransactionQuantity();
                break;
            case C_CORRIENTE:
                this.moneyAmount += money;
                this.incrementTransactionQuantity();
                break;
            case PLAZO_FIJO:
                LocalDateTime now = LocalDateTime.now();
                int currentDay = now.getDayOfMonth();

                if(currentDay != operationDay){
                    throw new Exception(String.format("You cannot perform this deposit today (%d), only on %d", currentDay, operationDay) );
                }
                if(transactionsLimit == transactionsQuantity){
                    throw new Exception(String.format("Your limit is %d and you've done %d until now", transactionsLimit, transactionsQuantity) );
                }

                this.moneyAmount += money;
                this.incrementTransactionQuantity();
                break;
        }
    }

    public void withDrawMoney(Double moneyAmount) throws Exception {
        if(moneyAmount > this.moneyAmount){
            throw new Exception("Error: insuficient money amount");
        }

        switch (accountType) {
            case AHORRO:
                if(transactionsLimit == transactionsQuantity){
                    throw new Exception(String.format("Your limit is %d and you've done %d until now", transactionsLimit, transactionsQuantity) );
                }

                this.moneyAmount -= moneyAmount;
                this.incrementTransactionQuantity();
                break;
            case C_CORRIENTE:
                this.moneyAmount -= moneyAmount;
                this.incrementTransactionQuantity();
                break;
            case PLAZO_FIJO:
                LocalDateTime now = LocalDateTime.now();
                int currentDay = now.getDayOfMonth();

                if(currentDay >= operationDay){
                    throw new Exception(String.format("You cannot perform this withdraw today (%d), only on %d", currentDay, operationDay) );
                }
                if(transactionsLimit == transactionsQuantity){
                    throw new Exception(String.format("Your limit is %d and you've done %d until now", transactionsLimit, transactionsQuantity) );
                }

                this.moneyAmount -= moneyAmount;
                this.incrementTransactionQuantity();
                break;
        }


    }

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
        newAccount.setOperationDay(accountCreationRequest.getOperationDay());

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
        newAccount.setOperationDay(null);

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
        newAccount.setOperationDay(null);

        return newAccount;
    }
}
