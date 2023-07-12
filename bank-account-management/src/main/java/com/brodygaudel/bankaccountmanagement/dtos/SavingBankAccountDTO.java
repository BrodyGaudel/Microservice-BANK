package com.brodygaudel.bankaccountmanagement.dtos;

import com.brodygaudel.bankaccountmanagement.enums.Currency;
import com.brodygaudel.bankaccountmanagement.enums.Status;


import java.math.BigDecimal;
import java.util.Date;

public class SavingBankAccountDTO extends BankAccountDTO {
    private String id;
    private BigDecimal amount;
    private Currency currency;
    private Status status;
    private Long customerId;
    private Date dateOfCreation;
    private Date dateOfLastUpdate;
    private double interestRate;

    public SavingBankAccountDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public void setDateOfLastUpdate(Date dateOfLastUpdate) {
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "SavingBankAccountDTO{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", status=" + status +
                ", customerId=" + customerId +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfLastUpdate=" + dateOfLastUpdate +
                ", interestRate=" + interestRate +
                '}';
    }
}
