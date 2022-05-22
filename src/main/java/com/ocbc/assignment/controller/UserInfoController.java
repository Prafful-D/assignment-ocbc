package com.ocbc.assignment.controller;

import com.ocbc.assignment.dto.ApplicationResponse;
import com.ocbc.assignment.dto.TransactionResponse;
import com.ocbc.assignment.dto.UserInfoDTO;
import com.ocbc.assignment.enitity.PendingTransaction;
import com.ocbc.assignment.enitity.Transaction;
import com.ocbc.assignment.enitity.UserInfo;
import com.ocbc.assignment.repo.PendingTransactionsRepository;
import com.ocbc.assignment.repo.TransactionsRepository;
import com.ocbc.assignment.repo.UsersRepository;
import com.ocbc.assignment.service.UsersService;
import com.ocbc.assignment.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author PraffulD
 */
@RestController
public class UserInfoController {

    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/users/{id}")
    public ApplicationResponse users(@PathVariable("id") UUID id) {
        return new ApplicationResponse<>(usersService.getUserInfo(id));
    }

    @PostMapping(value = "/users")
    public ApplicationResponse users(@RequestBody @Valid UserInfoDTO userInfo) {
        return new ApplicationResponse<>(usersService.create(userInfo));
    }
}
