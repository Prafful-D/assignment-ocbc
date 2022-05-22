package com.ocbc.assignment.dto;

import java.util.UUID;

/**
 * @author PraffulD
 */
public class TopUpDTO {

    private UUID id;

    private Double amount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
