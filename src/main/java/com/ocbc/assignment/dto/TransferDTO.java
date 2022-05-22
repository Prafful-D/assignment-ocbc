package com.ocbc.assignment.dto;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * @author PraffulD
 */
public class TransferDTO {

    @NonNull
    private UUID fromUser;

    @NonNull
    private UUID toUser;

    private Double amount;

    public UUID getToUser() {
        return toUser;
    }

    public void setToUser(UUID toUser) {
        this.toUser = toUser;
    }

    public UUID getFromUser() {
        return fromUser;
    }

    public void setFromUser(UUID fromUser) {
        this.fromUser = fromUser;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
