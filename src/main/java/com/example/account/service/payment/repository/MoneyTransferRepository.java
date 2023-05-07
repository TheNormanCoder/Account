package com.example.account.service.payment.repository;

import com.example.account.service.payment.response.MoneyTransfer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyTransferRepository extends MongoRepository<MoneyTransfer, String> {
    MoneyTransfer findByMoneyTransferId(String moneyTransferId);
}