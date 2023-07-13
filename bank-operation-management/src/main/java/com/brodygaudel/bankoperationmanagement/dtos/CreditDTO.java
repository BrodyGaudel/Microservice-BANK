package com.brodygaudel.bankoperationmanagement.dtos;

import java.math.BigDecimal;

public record CreditDTO(String accountId, BigDecimal amount, String description) {
}
