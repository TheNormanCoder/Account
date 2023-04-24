package com.example.account.service.transaction;

import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<TransactionResponse> getTransactions(String accountId, String fromDate, String toDate);
}
