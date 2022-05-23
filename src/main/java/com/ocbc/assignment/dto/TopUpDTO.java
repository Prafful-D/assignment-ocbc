package com.ocbc.assignment.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author PraffulD
 */
public class TopUpDTO {

    @NotNull(message = "UserId to TOPUP is required")
    private UUID id;

    @NotNull(message = "Amount is required")
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
