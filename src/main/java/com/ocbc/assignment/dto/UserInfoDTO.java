package com.ocbc.assignment.dto;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * @author PraffulD
 */
public class UserInfoDTO {

    private UUID id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "UserName cannot be empty")
    private String userName;

/*
    private String password;
*/

    private Double amount;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
