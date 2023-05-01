package com.example.account.service.transaction.response;

import java.util.List;

public class TransactionResponse {
    private List<Transaction> list;

    public List<Transaction> getList() {
        return list;
    }

    public void setList(List<Transaction> list) {
        this.list = list;
    }
}

