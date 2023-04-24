package com.example.account.service.payment.request;

import jakarta.validation.constraints.Pattern;

public class LegalPersonBeneficiary {
    @Pattern(regexp="^\\d{11}$", message="The fiscal code must be 11 digits long")
    private String fiscalCode;
    private String legalRepresentativeFiscalCode;

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setLegalRepresentativeFiscalCode(String legalRepresentativeFiscalCode) {
        this.legalRepresentativeFiscalCode = legalRepresentativeFiscalCode;
    }

    public String getLegalRepresentativeFiscalCode() {
        return legalRepresentativeFiscalCode;
    }
}
