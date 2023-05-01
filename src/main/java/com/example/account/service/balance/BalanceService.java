package com.example.account.service.balance;

import com.example.account.service.balance.response.BalanceResponse;
import org.springframework.http.ResponseEntity;

public interface BalanceService {
    ResponseEntity<BalanceResponse> getBalance(String accountId);
}
