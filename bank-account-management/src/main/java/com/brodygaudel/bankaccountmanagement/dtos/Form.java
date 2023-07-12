package com.brodygaudel.bankaccountmanagement.dtos;

import com.brodygaudel.bankaccountmanagement.enums.Status;

import java.math.BigDecimal;

public record Form(BigDecimal balance, Status status) { }
