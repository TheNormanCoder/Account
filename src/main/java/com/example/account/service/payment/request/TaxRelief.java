package com.example.account.service.payment.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class TaxRelief {
    private String taxReliefId;
    @NotNull(message = "isCondoUpgrade is required")
    private boolean condoUpgrade;
    @NotNull(message = "creditorFiscalCode is required")
    @Pattern(regexp = "^([A-Z0-9]{11}|[0-9]{11})$",
            message = "creditorFiscalCode must be a valid Codice Fiscale or Partita IVA")
    private String creditorFiscalCode;
    @NotNull(message = "beneficiaryType is required")
    @Pattern(regexp = "^(NATURAL_PERSON|LEGAL_PERSON)$", message = "beneficiaryType must be either NATURAL_PERSON or LEGAL_PERSON")
    private String beneficiaryType;
    private NaturalPersonBeneficiary naturalPersonBeneficiary;
    private LegalPersonBeneficiary legalPersonBeneficiary;

    public void setTaxReliefId(String taxReliefId) {
        this.taxReliefId = taxReliefId;
    }

    public String getTaxReliefId() {
        return taxReliefId;
    }

    public void setCondoUpgrade(boolean condoUpgrade) {
        this.condoUpgrade = condoUpgrade;
    }

    @AssertTrue(message = "isCondoUpgrade must be either true or false")
    public boolean isCondoUpgrade() {
        return condoUpgrade;
    }

    public void setCreditorFiscalCode(String creditorFiscalCode) {
        this.creditorFiscalCode = creditorFiscalCode;
    }

    public String getCreditorFiscalCode() {
        return creditorFiscalCode;
    }

    public void setBeneficiaryType(String beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public String getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setNaturalPersonBeneficiary(NaturalPersonBeneficiary naturalPersonBeneficiary) {
        this.naturalPersonBeneficiary = naturalPersonBeneficiary;
    }

    public NaturalPersonBeneficiary getNaturalPersonBeneficiary() {
        return naturalPersonBeneficiary;
    }

    public void setLegalPersonBeneficiary(LegalPersonBeneficiary legalPersonBeneficiary) {
        this.legalPersonBeneficiary = legalPersonBeneficiary;
    }

    public LegalPersonBeneficiary getLegalPersonBeneficiary() {
        return legalPersonBeneficiary;
    }
}
