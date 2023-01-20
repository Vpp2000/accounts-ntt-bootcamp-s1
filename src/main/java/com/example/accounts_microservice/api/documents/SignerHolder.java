package com.example.accounts_microservice.api.documents;

import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class SignerHolder {
    @NotEmpty
    @NonNull
    private String name;
    @NotEmpty
    @NonNull
    private String lastName;
    @NotEmpty
    @NonNull
    private String dni;
}
