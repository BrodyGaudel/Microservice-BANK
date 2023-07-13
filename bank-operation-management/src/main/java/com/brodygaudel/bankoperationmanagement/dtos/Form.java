package com.brodygaudel.bankoperationmanagement.dtos;

import com.brodygaudel.bankoperationmanagement.enums.Status;

import java.math.BigDecimal;

public record Form(BigDecimal balance, Status status) {
}
