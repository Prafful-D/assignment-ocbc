package com.ocbc.assignment.service;

import com.ocbc.assignment.dto.TopUpDTO;
import com.ocbc.assignment.dto.TransactionResponse;
import com.ocbc.assignment.dto.TransferDTO;
import com.ocbc.assignment.enitity.PendingTransaction;
import com.ocbc.assignment.enitity.Transaction;
import com.ocbc.assignment.enitity.UserInfo;
import com.ocbc.assignment.enums.TransactionType;
import com.ocbc.assignment.exception.ApplicationException;
import com.ocbc.assignment.repo.PendingTransactionsRepository;
import com.ocbc.assignment.repo.TransactionsRepository;
import com.ocbc.assignment.repo.UsersRepository;
import com.ocbc.assignment.service.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author PraffulD
 */
@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    @Mock
    private PendingTransactionsRepository pendingTransactionsRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFetchTransaction() {

        UUID id = UUID.randomUUID();

        Optional<UserInfo> userInfoOptional = getUserInfo(id, "prafful");
        List<PendingTransaction> toPayList = getPendingTransactions(id, UUID.randomUUID());
        List<PendingTransaction> toReceiveList = getPendingTransactions(UUID.randomUUID(), id);
        List<Transaction> transactions = getTransactions(id);

        Mockito.when(usersRepository.findById(id)).thenReturn(userInfoOptional);
        Mockito.when(pendingTransactionsRepository.getPendingTransactionByFromUserOrderByDate(id)).thenReturn(toPayList);
        Mockito.when(pendingTransactionsRepository.getPendingTransactionByToUserOrderByDate(id)).thenReturn(toReceiveList);
        Mockito.when(transactionsRepository.getTransactionByUserIdOrderByDate(id)).thenReturn(transactions);

        TransactionResponse transactionResponse = transactionService.fetchTransaction(id);
        assertResult(transactionResponse);
    }

    @Test
    public void testTransferNoBalance() {

        UUID fromId = UUID.randomUUID();
        UUID toId = UUID.randomUUID();

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setToUser(toId);
        transferDTO.setFromUser(fromId);
        transferDTO.setAmount(200.0);

        Optional<UserInfo> userInfoOptional1 = getUserInfo(fromId, "prafful");
        Optional<UserInfo> userInfoOptional2 = getUserInfo(fromId, "sam");


        List<PendingTransaction> toPayList = getPendingTransactions(fromId, UUID.randomUUID());
        List<PendingTransaction> toReceiveList = getPendingTransactions(UUID.randomUUID(), fromId);
        List<Transaction> transactions = getTransactions(fromId);

        Mockito.when(usersRepository.findById(fromId)).thenReturn(userInfoOptional1);
        Mockito.when(usersRepository.findById(toId)).thenReturn(userInfoOptional2);

        Mockito.when(pendingTransactionsRepository.getPendingTransactionByFromUserOrderByDate(fromId)).thenReturn(toPayList);
        Mockito.when(pendingTransactionsRepository.getPendingTransactionByToUserOrderByDate(fromId)).thenReturn(toReceiveList);
        Mockito.when(transactionsRepository.getTransactionByUserIdOrderByDate(fromId)).thenReturn(transactions);

        TransactionResponse transactionResponse = transactionService.transfer(transferDTO);
        assertResult(transactionResponse);
    }

    @Test(expected = ApplicationException.class)
    public void testTransferException() {

        UUID fromId = UUID.randomUUID();
        UUID toId = UUID.randomUUID();

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setToUser(toId);
        transferDTO.setFromUser(fromId);
        transferDTO.setAmount(200.0);

        transactionService.transfer(transferDTO);
    }

    @Test
    public void testTransfer() {

        UUID fromId = UUID.randomUUID();
        UUID toId = UUID.randomUUID();

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setToUser(toId);
        transferDTO.setFromUser(fromId);
        transferDTO.setAmount(100.0);

        Optional<UserInfo> userInfoOptional1 = getUserInfo(fromId, "prafful");
        Optional<UserInfo> userInfoOptional2 = getUserInfo(fromId, "sam");


        List<PendingTransaction> toPayList = getPendingTransactions(fromId, UUID.randomUUID());
        List<PendingTransaction> toReceiveList = getPendingTransactions(UUID.randomUUID(), fromId);
        List<Transaction> transactions = getTransactions(fromId);

        Mockito.when(usersRepository.findById(fromId)).thenReturn(userInfoOptional1);
        Mockito.when(usersRepository.findById(toId)).thenReturn(userInfoOptional2);

        Mockito.when(pendingTransactionsRepository.getPendingTransactionByFromUserOrderByDate(fromId)).thenReturn(toPayList);
        Mockito.when(pendingTransactionsRepository.getPendingTransactionByToUserOrderByDate(fromId)).thenReturn(toReceiveList);
        Mockito.when(transactionsRepository.getTransactionByUserIdOrderByDate(fromId)).thenReturn(transactions);

        TransactionResponse transactionResponse = transactionService.transfer(transferDTO);
        assertResult(transactionResponse);
    }

    @Test
    public void testTopUp() {

        UUID fromId = UUID.randomUUID();
        UUID toId = UUID.randomUUID();

        TopUpDTO topUpDTO = new TopUpDTO();
        topUpDTO.setId(fromId);
        topUpDTO.setAmount(200.0);

        Optional<UserInfo> userInfoOptional1 = getUserInfo(fromId, "prafful");
        Optional<UserInfo> userInfoOptional2 = getUserInfo(fromId, "sam");

        List<PendingTransaction> toPayList = getPendingTransactions(fromId, toId);
        List<PendingTransaction> toReceiveList = getPendingTransactions(toId, fromId);
        List<Transaction> transactions = getTransactions(fromId);

        Mockito.when(usersRepository.findById(fromId)).thenReturn(userInfoOptional1);
        Mockito.when(usersRepository.findById(toId)).thenReturn(userInfoOptional2);

        Mockito.when(pendingTransactionsRepository.getPendingTransactionByFromUserOrderByDate(fromId)).thenReturn(toPayList);
        Mockito.when(pendingTransactionsRepository.getPendingTransactionByToUserOrderByDate(fromId)).thenReturn(toReceiveList);
        Mockito.when(transactionsRepository.getTransactionByUserIdOrderByDate(fromId)).thenReturn(transactions);

        TransactionResponse transactionResponse = transactionService.topUp(topUpDTO);
        assertResult(transactionResponse);
    }

    @Test
    public void testTopUp_MorePending() {

        UUID fromId = UUID.randomUUID();
        UUID toId = UUID.randomUUID();

        TopUpDTO topUpDTO = new TopUpDTO();
        topUpDTO.setId(fromId);
        topUpDTO.setAmount(-10.0);

        Optional<UserInfo> userInfoOptional1 = getUserInfo(fromId, "prafful");
        Optional<UserInfo> userInfoOptional2 = getUserInfo(fromId, "sam");

        List<PendingTransaction> toPayList = getPendingTransactions(fromId, toId);
        List<PendingTransaction> toReceiveList = getPendingTransactions(toId, fromId);
        List<Transaction> transactions = getTransactions(fromId);

        Mockito.when(usersRepository.findById(fromId)).thenReturn(userInfoOptional1);
        Mockito.when(usersRepository.findById(toId)).thenReturn(userInfoOptional2);

        Mockito.when(pendingTransactionsRepository.getPendingTransactionByFromUserOrderByDate(fromId)).thenReturn(toPayList);
        Mockito.when(pendingTransactionsRepository.getPendingTransactionByToUserOrderByDate(fromId)).thenReturn(toReceiveList);
        Mockito.when(transactionsRepository.getTransactionByUserIdOrderByDate(fromId)).thenReturn(transactions);

        TransactionResponse transactionResponse = transactionService.topUp(topUpDTO);
        assertResult(transactionResponse);
    }

    private void assertResult(TransactionResponse transactionResponse) {
        Assert.assertNotNull(transactionResponse);
        Assert.assertNotNull(transactionResponse.getToPay());
        Assert.assertNotNull(transactionResponse.getToReceive());
        Assert.assertNotNull(transactionResponse.getTransactions());
        Assert.assertEquals(1, transactionResponse.getToPay().size());
        Assert.assertEquals(1, transactionResponse.getToReceive().size());
        Assert.assertEquals(1, transactionResponse.getTransactions().size());
    }

    private List<Transaction> getTransactions(UUID id) {
        Transaction transaction = new Transaction();
        transaction.setUserId(id);
        transaction.setAmount(100.0);
        transaction.setTransactionType(TransactionType.CREDIT);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        return transactions;
    }

    private List<PendingTransaction> getPendingTransactions(UUID id, UUID toUser) {
        PendingTransaction toPay = new PendingTransaction();
        toPay.setFromUser(id);
        toPay.setToUser(toUser);
        toPay.setAmount(100.0);
        List<PendingTransaction> toPayList = new ArrayList<>();
        toPayList.add(toPay);
        return toPayList;
    }

    private Optional<UserInfo> getUserInfo(UUID uuid, String name) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAmount(100.0);
        userInfo.setUserName(name);
        userInfo.setName("PD");
        userInfo.setId(uuid);

        Optional<UserInfo> userInfoOptional = Optional.of(userInfo);
        return userInfoOptional;
    }
}
