package com.brodygaudel.bankoperationmanagement.dtos;

import java.math.BigDecimal;

public record TransferDTO(String accountIdSource,
                          String accountIdDestination,
                          BigDecimal amount,
                          String description) {
}
