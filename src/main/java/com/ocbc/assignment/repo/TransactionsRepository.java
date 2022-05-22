package com.ocbc.assignment.repo;

import com.ocbc.assignment.enitity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author PraffulD
 */
@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> getTransactionByUserIdOrderByDate(UUID userId);
}
