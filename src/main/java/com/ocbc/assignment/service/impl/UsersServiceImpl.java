package com.ocbc.assignment.service.impl;

import com.ocbc.assignment.dto.TransactionResponse;
import com.ocbc.assignment.dto.UserInfoDTO;
import com.ocbc.assignment.enitity.UserInfo;
import com.ocbc.assignment.exception.ApplicationException;
import com.ocbc.assignment.repo.UsersRepository;
import com.ocbc.assignment.service.TransactionService;
import com.ocbc.assignment.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author PraffulD
 */
@Service
public class UsersServiceImpl implements UsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public TransactionResponse getUserInfo(UUID uuid) {
        return transactionService.extractTransactionResponse(uuid);
    }

    @Override
    public UserInfoDTO create(UserInfoDTO userInfoDTO) {

        if (usersRepository.findByUserName(userInfoDTO.getUserName()) != null) {
            LOGGER.error("User name already taken {}", userInfoDTO.getUserName());
            throw new ApplicationException("1001", "Please use different user name");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        UserInfo userInfoResult = usersRepository.save(userInfo);
        BeanUtils.copyProperties(userInfoResult, userInfoDTO);

        return userInfoDTO;
    }
}
