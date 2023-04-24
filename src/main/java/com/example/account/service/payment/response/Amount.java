package com.example.account.service.payment.response;

public class Amount {
    private double debtorAmount;
    private String debtorCurrency;
    private double creditorAmount;
    private String creditorCurrency;
    private String creditorCurrencyDate;
    private int exchangeRate;


    public Amount() {
    }

    public double getDebtorAmount() {
        return debtorAmount;
    }

    public void setDebtorAmount(double debtorAmount) {
        this.debtorAmount = debtorAmount;
    }

    public String getDebtorCurrency() {
        return debtorCurrency;
    }

    public void setDebtorCurrency(String debtorCurrency) {
        this.debtorCurrency = debtorCurrency;
    }

    public double getCreditorAmount() {
        return creditorAmount;
    }

    public void setCreditorAmount(double creditorAmount) {
        this.creditorAmount = creditorAmount;
    }

    public String getCreditorCurrency() {
        return creditorCurrency;
    }

    public void setCreditorCurrency(String creditorCurrency) {
        this.creditorCurrency = creditorCurrency;
    }

    public String getCreditorCurrencyDate() {
        return creditorCurrencyDate;
    }

    public void setCreditorCurrencyDate(String creditorCurrencyDate) {
        this.creditorCurrencyDate = creditorCurrencyDate;
    }

    public int getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(int exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
