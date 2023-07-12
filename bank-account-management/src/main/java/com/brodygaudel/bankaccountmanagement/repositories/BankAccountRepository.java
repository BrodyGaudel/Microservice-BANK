package com.brodygaudel.bankaccountmanagement.repositories;

import com.brodygaudel.bankaccountmanagement.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    @Query("select b from BankAccount b where b.customerId =?1")
    List<BankAccount> findByCustomerId(Long customerId);
}
