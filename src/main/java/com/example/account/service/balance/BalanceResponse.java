package com.example.account.service.balance;

public class BalanceResponse {
    private String date;
    private double balance;
    private double availableBalance;
    private String currency;

    public BalanceResponse() {
    }

    public BalanceResponse(double v) {
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
