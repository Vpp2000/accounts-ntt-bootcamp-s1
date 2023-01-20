package com.example.accounts_microservice.api.documents;

import com.example.accounts_microservice.api.dto.account_creation.AccountCreationRequest;
import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


// REPRESENTA LOS PRODUCOS PASIVOS, ES DECIR, CUENTAS BANCARIAS
@Document("accounts")
@NoArgsConstructor
@Data
public class Account {

    private static final Logger logger_console = LoggerFactory.getLogger("root");

    @Value("{account.trans_limit}")
    private static int transactionsLimit;

    @Id
    private String id;

    private ClientType clientType;
    private ProductType accountType;  // SE QUEDA
    private UUID accountNumber;     // SE QUEDA
    private String customerId;   //  SE QUEDA
    //private Double maintainanceComission;   // SE VA PORQUE ESTARÁ EN EL YAML
    private Double moneyAmount;   // QUEDA
    //private Integer transactionsLimit;  // SE VA , ESTA EN EL YAML
    private Integer transactionsQuantity = 0;  // queda
    private Integer operationDay;  // queda

    private List<SignerHolder> holders;  // queda
    private List<SignerHolder> signers; // queda

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
        newAccount.setMoneyAmount(0.0);
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
        newAccount.setMoneyAmount(0.0);
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
        newAccount.setMoneyAmount(0.0);
        newAccount.setTransactionsQuantity(0);
        newAccount.setOperationDay(null);

        return newAccount;
    }
}
