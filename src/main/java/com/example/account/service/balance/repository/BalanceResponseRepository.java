package com.example.account.service.balance.repository;


import com.example.account.service.balance.response.BalanceResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceResponseRepository extends MongoRepository<BalanceResponse, String> {
}