package com.ocbc.assignment.service.impl;

import com.ocbc.assignment.dto.PendingTransactionDTO;
import com.ocbc.assignment.dto.TopUpDTO;
import com.ocbc.assignment.dto.TransactionDTO;
import com.ocbc.assignment.dto.TransactionResponse;
import com.ocbc.assignment.dto.TransferDTO;
import com.ocbc.assignment.dto.UserInfoDTO;
import com.ocbc.assignment.enitity.PendingTransaction;
import com.ocbc.assignment.enitity.Transaction;
import com.ocbc.assignment.enitity.UserInfo;
import com.ocbc.assignment.enums.TransactionType;
import com.ocbc.assignment.exception.ApplicationException;
import com.ocbc.assignment.repo.PendingTransactionsRepository;
import com.ocbc.assignment.repo.TransactionsRepository;
import com.ocbc.assignment.repo.UsersRepository;
import com.ocbc.assignment.service.TransactionService;
import com.ocbc.assignment.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author PraffulD
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private PendingTransactionsRepository pendingTransactionsRepository;

    @Override
    public TransactionResponse fetchTransaction(UUID userId) {
        return extractTransactionResponse(userId);
    }

    @Override
    @Transactional
    public TransactionResponse transfer(TransferDTO transferDTO) {

        LOGGER.info("Starting executing transfer between from [{}]  to [{}]", transferDTO.getFromUser(), transferDTO.getToUser());

        Optional<UserInfo> fromUserOptional = usersRepository.findById(transferDTO.getFromUser());
        Optional<UserInfo> toUserOptional = usersRepository.findById(transferDTO.getToUser());

        if (fromUserOptional.isPresent() && toUserOptional.isPresent()) {

            UserInfo fromUserInfo = fromUserOptional.get();
            UserInfo toUserInfo = toUserOptional.get();

            Double currentBalance = fromUserInfo.getAmount();
            Double amtToTransfer = transferDTO.getAmount();

            if (currentBalance - amtToTransfer < 0) {
                saveTransactions(toUserInfo, fromUserInfo, currentBalance);

                Double pendingAmount = amtToTransfer - currentBalance;
                updatePendingTransactions(fromUserInfo, toUserInfo, pendingAmount);
            } else {
                saveTransactions(toUserInfo, fromUserInfo, amtToTransfer);
            }

            LOGGER.info("Completed Transfer for [{}]  to [{}]", transferDTO.getFromUser());

            return extractTransactionResponse(fromUserInfo.getId());
        }
        LOGGER.info("Invalid transaction, no record found for user [[]] or [{}]", transferDTO.getFromUser(), transferDTO.getToUser());

        throw new ApplicationException("NOT_FOUND", "Invalid transaction, no record found for user [[" + transferDTO.getFromUser() + "]] or [{" + transferDTO.getToUser() + "}]");
    }

    @Override
    @Transactional
    public TransactionResponse topUp(TopUpDTO topUpDTO) {

        LOGGER.info("Starting topUp for user [{}] ", topUpDTO.getId());

        Double amtToCredit = topUpDTO.getAmount();

        Optional<UserInfo> userOptional = usersRepository.findById(topUpDTO.getId());
        if (userOptional.isPresent()) {

            UserInfo fromUserInfo = userOptional.get();

            Double availableAmount = fromUserInfo.getAmount() + amtToCredit;
            fromUserInfo.setAmount(availableAmount);
            usersRepository.save(fromUserInfo);

            Transaction toTransaction = getTransaction(fromUserInfo.getId(), amtToCredit, TransactionType.CREDIT);
            transactionsRepository.save(toTransaction);

            LOGGER.info("Updated topup to master user ");

            List<PendingTransaction> pendingTransactions = pendingTransactionsRepository.getPendingTransactionByFromUserOrderByDate(fromUserInfo.getId());

            LOGGER.info("Pending transactions for user [{}] are {}", fromUserInfo.getId(), pendingTransactions.size());
            for (PendingTransaction pendingTransaction : pendingTransactions) {
                UserInfo toUser = usersRepository.findById(pendingTransaction.getToUser()).get();

                Double toPay = pendingTransaction.getAmount();
                if (toPay > availableAmount) {

                    toUser.setAmount(toUser.getAmount() + availableAmount);
                    usersRepository.save(toUser);

                    Transaction transactionToUser = getTransaction(pendingTransaction.getToUser(), availableAmount, TransactionType.CREDIT);
                    transactionsRepository.save(transactionToUser);

                    pendingTransaction.setUpdateDate(DateUtils.currentDate());
                    pendingTransaction.setAmount(pendingTransaction.getAmount() - availableAmount);

                    pendingTransactionsRepository.save(pendingTransaction);

                    Transaction transactionFromUser = getTransaction(fromUserInfo.getId(), availableAmount, TransactionType.DEBIT);
                    transactionsRepository.save(transactionFromUser);

                    availableAmount = 0.0;
                    break;

                } else {

                    availableAmount = availableAmount - toPay;
                    toUser.setAmount(toUser.getAmount() + toPay);
                    usersRepository.save(toUser);

                    Transaction transactionToUser = getTransaction(pendingTransaction.getToUser(), toPay, TransactionType.CREDIT);
                    transactionsRepository.save(transactionToUser);

                    Transaction transactionFromUser = getTransaction(fromUserInfo.getId(), toPay, TransactionType.DEBIT);
                    transactionsRepository.save(transactionFromUser);

                    pendingTransactionsRepository.deleteById(pendingTransaction.getId());

                }
            }
            fromUserInfo.setAmount(availableAmount);
            usersRepository.save(fromUserInfo);

            LOGGER.info("TopUp SUCCESS for user [{}] ", topUpDTO.getId());

            return extractTransactionResponse(fromUserInfo.getId());
        }

        throw new ApplicationException("NOT_FOUND", "Invalid transaction, no record found for user [[" + topUpDTO.getId() + "]] ");
    }

    @Override
    @Transactional
    public TransactionResponse extractTransactionResponse(UUID userId) {

        Optional<UserInfo> userInfoOptional = usersRepository.findById(userId);
        if (userInfoOptional.isPresent()) {
            TransactionResponse transactionResponse = new TransactionResponse();
            UserInfoDTO user = getUserInfoDTO(userInfoOptional.get());
            transactionResponse.setUser(user);

            List<PendingTransaction> pendingOwedTransaction = pendingTransactionsRepository.getPendingTransactionByFromUserOrderByDate(userId);
            List<PendingTransactionDTO> pendingOwedTransactionDTOS = getPendingTransactionDTOS(pendingOwedTransaction);

            List<PendingTransaction> pendingToReceiveTransaction = pendingTransactionsRepository.getPendingTransactionByToUserOrderByDate(userId);
            List<PendingTransactionDTO> pendingToReceiveTransactionDTOS = getPendingTransactionDTOS(pendingToReceiveTransaction);

            List<TransactionDTO> dtos = getTransactionDTOS(transactionsRepository.getTransactionByUserIdOrderByDate(userId));
            transactionResponse.setToPay(pendingOwedTransactionDTOS);
            transactionResponse.setToReceive(pendingToReceiveTransactionDTOS);

            transactionResponse.setTransactions(dtos);

            return transactionResponse;
        }

        throw new ApplicationException("NOT_FOUND", "No record found for user [[" + userId + "]] ");
    }

    private List<PendingTransactionDTO> getPendingTransactionDTOS(List<PendingTransaction> transactions) {
        List<PendingTransactionDTO> dtos = new ArrayList<>();
        for (PendingTransaction transaction : transactions) {
            PendingTransactionDTO transactionDTO = new PendingTransactionDTO();
            BeanUtils.copyProperties(transaction, transactionDTO);

            dtos.add(transactionDTO);
        }
        return dtos;
    }

    private List<TransactionDTO> getTransactionDTOS(List<Transaction> transactions) {
        List<TransactionDTO> dtos = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionDTO transactionDTO = new TransactionDTO();
            BeanUtils.copyProperties(transaction, transactionDTO);

            dtos.add(transactionDTO);
        }
        return dtos;
    }

    private UserInfoDTO getUserInfoDTO(UserInfo userInfo) {
        UserInfoDTO dto = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, dto);

        return dto;
    }

    private Transaction getTransaction(UUID userId, Double amount, TransactionType transactionType) {

        Transaction toTransaction = new Transaction();
        toTransaction.setAmount(amount);
        toTransaction.setTransactionType(transactionType);
        toTransaction.setUserId(userId);
        toTransaction.setDate(DateUtils.currentDate());

        return toTransaction;
    }

    private void saveTransactions(UserInfo toUserInfo, UserInfo fromUserInfo, Double amtToTransfer) {

        toUserInfo.setAmount(toUserInfo.getAmount() + amtToTransfer);
        usersRepository.save(toUserInfo);

        if (fromUserInfo.getAmount() - amtToTransfer < 0) {
            fromUserInfo.setAmount(0.0);
        } else {
            fromUserInfo.setAmount(fromUserInfo.getAmount() - amtToTransfer);
        }
        usersRepository.save(fromUserInfo);

        Transaction toTransaction = getTransaction(toUserInfo.getId(), amtToTransfer, TransactionType.CREDIT);
        transactionsRepository.save(toTransaction);

        Transaction fromTransaction = getTransaction(fromUserInfo.getId(), -amtToTransfer, TransactionType.DEBIT);
        transactionsRepository.save(fromTransaction);
    }

    private void updatePendingTransactions(UserInfo fromUserInfo, UserInfo toUserInfo, Double pendingAmount) {

        PendingTransaction pendingTransaction = new PendingTransaction();
        pendingTransaction.setAmount(pendingAmount);
        pendingTransaction.setDate(DateUtils.currentDate());
        pendingTransaction.setFromUser(fromUserInfo.getId());
        pendingTransaction.setToUser(toUserInfo.getId());
        pendingTransactionsRepository.save(pendingTransaction);
    }
}
