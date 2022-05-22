package com.ocbc.assignment.dto;

import com.ocbc.assignment.enums.TransactionType;

import java.util.Date;
import java.util.UUID;

/**
 * @author PraffulD
 */
public class TransactionDTO {

    private UUID id;

    private UUID userId;

    private TransactionType transactionType;

    private Double amount;

    private Date date;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
