package com.example.account.service.transaction;

import com.example.account.service.transaction.response.TransactionResponse;
import org.springframework.http.ResponseEntity;

public interface TransactionService {
    ResponseEntity<TransactionResponse> getTransactions(String accountId, String fromDate, String toDate);
}
