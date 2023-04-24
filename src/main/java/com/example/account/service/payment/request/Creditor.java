package com.example.account.service.payment.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Creditor {
    @NotNull
    @Size(max = 70)
    private String name;
    @NotNull
    private Account account;
    private Address address;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
