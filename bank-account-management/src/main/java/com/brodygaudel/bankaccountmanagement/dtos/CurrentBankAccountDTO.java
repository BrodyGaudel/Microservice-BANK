package com.brodygaudel.bankaccountmanagement.dtos;

import com.brodygaudel.bankaccountmanagement.enums.Currency;
import com.brodygaudel.bankaccountmanagement.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CurrentBankAccountDTO extends BankAccountDTO {
    private String id;
    private BigDecimal amount;
    private Currency currency;
    private Status status;
    private Long customerId;
    private Date dateOfCreation;
    private Date dateOfLastUpdate;
    private double overDraft;
}
