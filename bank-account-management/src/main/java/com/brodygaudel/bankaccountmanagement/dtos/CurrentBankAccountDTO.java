package com.brodygaudel.bankaccountmanagement.dtos;

import com.brodygaudel.bankaccountmanagement.enums.Currency;
import com.brodygaudel.bankaccountmanagement.enums.Status;

import java.math.BigDecimal;
import java.util.Date;

public class CurrentBankAccountDTO extends BankAccountDTO {
    private String id;
    private BigDecimal amount;
    private Currency currency;
    private Status status;
    private Long customerId;
    private Date dateOfCreation;
    private Date dateOfLastUpdate;
    private double overDraft;

    public CurrentBankAccountDTO() {
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

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }

    @Override
    public String toString() {
        return "CurrentBankAccountDTO{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", status=" + status +
                ", customerId=" + customerId +
                ", dateOfCreation=" + dateOfCreation +
                ", dateOfLastUpdate=" + dateOfLastUpdate +
                ", overDraft=" + overDraft +
                '}';
    }
}
