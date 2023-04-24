package com.example.account.service.payment;

import com.example.account.service.payment.request.Payment;
import com.example.account.service.payment.response.MoneyTransfer;
import org.springframework.http.ResponseEntity;

public interface PaymentService {

    public ResponseEntity<MoneyTransfer> executePayment(String accountId,Payment request);
}
