package com.ocbc.assignment.service;

import com.ocbc.assignment.dto.TransactionResponse;
import com.ocbc.assignment.dto.UserInfoDTO;

import java.util.UUID;

/**
 * @author PraffulD
 */
public interface UsersService {

    TransactionResponse getUserInfo(UUID uuid);

    UserInfoDTO create(UserInfoDTO userInfoDTO);
}
