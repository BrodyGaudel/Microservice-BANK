package com.brodygaudel.bankoperationmanagement.dtos;

import java.math.BigDecimal;

public record DebitDTO(String accountId, BigDecimal amount, String description) {
}
