package com.example.account.service.balance;

import org.springframework.http.ResponseEntity;

public interface BalanceService {
    ResponseEntity<BalanceResponse> getBalance(String accountId);
}
