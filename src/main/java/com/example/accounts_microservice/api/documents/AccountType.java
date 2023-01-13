package com.example.accounts_microservice.api.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="account_type")
@Data
public class AccountType {
    @Id
    private String id;

    @Indexed(unique = true)
    private int accountTypeCode;

    @Indexed(unique = true)
    private String description;
}
