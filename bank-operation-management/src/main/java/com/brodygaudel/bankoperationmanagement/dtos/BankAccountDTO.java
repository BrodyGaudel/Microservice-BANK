package com.brodygaudel.bankoperationmanagement.dtos;

import com.brodygaudel.bankoperationmanagement.enums.Status;

import java.math.BigDecimal;
import java.util.Date;

public record BankAccountDTO(
        String type,
        String id,
        BigDecimal amount,
        String currency,
        Status status,
        String customerId,
        Date dateOfCreation,
        Date dateOfLastUpdate,
        double interestRate,
        double overDraft

) {
}
