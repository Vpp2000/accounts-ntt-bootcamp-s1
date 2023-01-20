package com.example.accounts_microservice.api.dto.account_creation;

import com.example.accounts_microservice.api.documents.SignerHolder;
import com.example.accounts_microservice.api.enums.ClientType;
import com.example.accounts_microservice.api.enums.ProductType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;


@Data
public class CompanyAccountCreationRequest {
    @NotNull
    private String customerId;
    @NotNull
    private Double moneyAmount;

    @NotNull
    @Size(min = 1)
    private List<SignerHolder> holders;
    @NotNull
    private List<SignerHolder> signers;

}
