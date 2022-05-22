package com.ocbc.assignment.service;

import com.ocbc.assignment.dto.PendingTransactionDTO;
import com.ocbc.assignment.dto.TopUpDTO;
import com.ocbc.assignment.dto.TransactionDTO;
import com.ocbc.assignment.dto.TransactionResponse;
import com.ocbc.assignment.dto.TransferDTO;

import java.util.List;
import java.util.UUID;

/**
 * @author PraffulD
 */
public interface TransactionService {

    TransactionResponse fetchTransaction(UUID userId);

    TransactionResponse transfer(TransferDTO transferDTO);

    TransactionResponse topUp(TopUpDTO topUpDTO);

    TransactionResponse extractTransactionResponse(UUID userId);
}
