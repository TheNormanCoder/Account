package com.example.account.service.transaction.repository;


import com.example.account.service.transaction.response.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionResponseRepository extends MongoRepository<Transaction, String> {
}