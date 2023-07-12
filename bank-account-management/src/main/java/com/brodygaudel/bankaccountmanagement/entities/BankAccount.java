package com.brodygaudel.bankaccountmanagement.entities;

import com.brodygaudel.bankaccountmanagement.enums.Currency;
import com.brodygaudel.bankaccountmanagement.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4, discriminatorType = DiscriminatorType.STRING)
public abstract class BankAccount {
    @Id
    private String id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(unique = true, nullable = false)
    private Long customerId;

    private Date dateOfCreation;
    private Date dateOfLastUpdate;


}
