package com.brodygaudel.bankoperationmanagement.dtos;

import com.brodygaudel.bankoperationmanagement.enums.Type;

import java.math.BigDecimal;
import java.util.Date;

public record OperationDTO(String id, BigDecimal amount, Type type,
                           String description, Date date, String accountId) { }
