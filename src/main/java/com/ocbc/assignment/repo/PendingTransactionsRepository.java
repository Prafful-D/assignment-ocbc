package com.ocbc.assignment.repo;

import com.ocbc.assignment.enitity.PendingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author PraffulD
 */
@Repository
public interface PendingTransactionsRepository extends JpaRepository<PendingTransaction, UUID> {

    List<PendingTransaction> getPendingTransactionByFromUserOrderByDate(UUID userId);

    List<PendingTransaction> getPendingTransactionByToUserOrderByDate(UUID userId);
}
