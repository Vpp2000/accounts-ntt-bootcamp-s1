package com.example.accounts_microservice.api.documents;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


// represents passive products
@Document("accounts")
@Data
public class Account {
    @Id
    private String id;
    private int accountCode;
    private String customerId;

    private Double maintainanceComission;

    private Double moneyAmount;

    private int monthlyTransactions;

    private LocalDateTime operationDate;

}
