package com.ocbc.assignment.service;

import com.ocbc.assignment.dto.TransactionResponse;
import com.ocbc.assignment.dto.UserInfoDTO;
import com.ocbc.assignment.enitity.PendingTransaction;
import com.ocbc.assignment.enitity.Transaction;
import com.ocbc.assignment.enitity.UserInfo;
import com.ocbc.assignment.exception.ApplicationException;
import com.ocbc.assignment.repo.UsersRepository;
import com.ocbc.assignment.service.impl.UsersServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author PraffulD
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    public void testCreateUser() {

        UUID id = UUID.randomUUID();

        //Optional<UserInfo> userInfoOptional = getUserInfo(id, "prafful");

        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setName("PD");
        userInfo.setUserName("prafful");
        Mockito.when(usersRepository.save(Mockito.any(UserInfo.class))).thenReturn(userInfo);

        UserInfoDTO dto = new UserInfoDTO();
        dto.setAmount(100.0);
        dto.setUserName("prafful");
        dto.setName("PD");
        UserInfoDTO response = usersService.create(dto);

        Assert.assertNotNull(response);
        Assert.assertEquals("prafful", response.getUserName());
        Assert.assertEquals("PD", response.getName());

    }

    @Test(expected = ApplicationException.class)
    public void testCreateUserException() {

        UUID id = UUID.randomUUID();

        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setName("PD");
        userInfo.setUserName("prafful");
        Mockito.when(usersRepository.findByUserName("prafful")).thenReturn(userInfo);

        UserInfoDTO dto = new UserInfoDTO();
        dto.setAmount(100.0);
        dto.setUserName("prafful");
        dto.setName("PD");
        UserInfoDTO response = usersService.create(dto);

    }
}
