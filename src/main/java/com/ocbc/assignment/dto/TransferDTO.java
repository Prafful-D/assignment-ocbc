package com.ocbc.assignment.dto;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * @author PraffulD
 */
public class TransferDTO {

    @NotEmpty(message = "Transfer from user id is required")
    private UUID fromUser;

    @NotEmpty(message = "Transfer to user id is required")
    private UUID toUser;

    @NotEmpty(message = "Amount cannot be empty")
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
