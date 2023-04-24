package com.example.account.service.payment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class NaturalPersonBeneficiary {
    @NotBlank(message = "fiscalCode1 is required")
    @Pattern(regexp = "^[A-Z0-9]{16}$", message = "fiscalCode1 must be a valid Italian Codice Fiscale")
    private String fiscalCode1;


    public void setFiscalCode1(String fiscalCode1) {
        this.fiscalCode1 = fiscalCode1;
    }

    public String getFiscalCode1() {
        return fiscalCode1;
    }
}
