package com.example.account.service.payment.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class Payment {
    private String executionDate;
    private String uri;
    @NotNull
    @Size(max = 140)
    private String description;
    @NotNull
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;
    @NotBlank(message = "currency required")
    private String currency;
    private boolean urgent;
    private boolean instant;
    private String feeType;
    private String feeAccountId;
    private Creditor creditor;
    private TaxRelief taxRelief;

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setInstant(boolean instant) {
        this.instant = instant;
    }

    public boolean isInstant() {
        return instant;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeAccountId(String feeAccountId) {
        this.feeAccountId = feeAccountId;
    }

    public String getFeeAccountId() {
        return feeAccountId;
    }

    public void setCreditor(Creditor creditor) {
        this.creditor = creditor;
    }

    public Creditor getCreditor() {
        return creditor;
    }

    public void setTaxRelief(TaxRelief taxRelief) {
        this.taxRelief = taxRelief;
    }

    public TaxRelief getTaxRelief() {
        return taxRelief;
    }
}
