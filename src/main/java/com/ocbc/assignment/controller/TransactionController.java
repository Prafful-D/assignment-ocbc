package com.ocbc.assignment.controller;

import com.ocbc.assignment.dto.ApplicationResponse;
import com.ocbc.assignment.dto.TopUpDTO;
import com.ocbc.assignment.dto.TransactionResponse;
import com.ocbc.assignment.dto.TransferDTO;
import com.ocbc.assignment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author PraffulD
 */
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/transactions/users/{userId}")
    public ApplicationResponse getTransaction(@PathVariable("userId") UUID userId) {
        return new ApplicationResponse<>(transactionService.fetchTransaction(userId));
    }

    @PostMapping(value = "/transfer")
    public ApplicationResponse transfer(@Validated @RequestBody TransferDTO transferDTO) {
        return new ApplicationResponse<>(transactionService.transfer(transferDTO));
    }

    @PostMapping(value = "/topUp")
    public ApplicationResponse topUp(@RequestBody TopUpDTO topUpDTO) {
        return new ApplicationResponse<>(transactionService.topUp(topUpDTO));
    }
}
