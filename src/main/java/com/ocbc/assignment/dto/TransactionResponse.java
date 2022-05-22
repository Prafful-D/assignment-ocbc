package com.ocbc.assignment.dto;

import java.util.List;

/**
 * @author PraffulD
 */
public class TransactionResponse {

    private UserInfoDTO user;
    private List<TransactionDTO> transactions;

    private List<PendingTransactionDTO> toPay;

    private List<PendingTransactionDTO> toReceive;

    public UserInfoDTO getUser() {
        return user;
    }

    public void setUser(UserInfoDTO user) {
        this.user = user;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public List<PendingTransactionDTO> getToPay() {
        return toPay;
    }

    public void setToPay(List<PendingTransactionDTO> toPay) {
        this.toPay = toPay;
    }

    public List<PendingTransactionDTO> getToReceive() {
        return toReceive;
    }

    public void setToReceive(List<PendingTransactionDTO> toReceive) {
        this.toReceive = toReceive;
    }
}
