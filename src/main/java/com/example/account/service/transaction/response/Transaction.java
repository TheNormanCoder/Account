package com.example.account.service.transaction.response;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Objects;

@Document(collection = "Transaction")
public class Transaction {
    @Id
    private String id;
    private String transactionId;
    private String operationId;
    private String accountingDate;
    private String valueDate;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String description;

    public Transaction() {
    }
    public Transaction(String transactionId, String description, BigDecimal amount) {
        this.transactionId = transactionId;
        this.description = description;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(String accountingDate) {
        this.accountingDate = accountingDate;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(operationId, that.operationId) &&
                Objects.equals(accountingDate, that.accountingDate) &&
                Objects.equals(valueDate, that.valueDate) &&
                Objects.equals(type, that.type) &&
                (amount == null ? that.amount == null : amount.compareTo(that.amount) == 0) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(description, that.description);
    }

}
