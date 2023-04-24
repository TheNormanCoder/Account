package com.example.account.service.payment.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Account {
    private String bicCode;
    @NotNull
    @Pattern(regexp="[A-Z]{2}\\d{2}[A-Z]{4}\\d{10}")
    private String accountCode;


    public void setBicCode(String bicCode) {
        this.bicCode = bicCode;
    }

    public String getBicCode() {
        return bicCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountCode() {
        return accountCode;
    }
}
