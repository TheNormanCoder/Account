package com.example.account.service.payment.response;

public class Debtor {
    private String name;
    private Account account;

    public Debtor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
