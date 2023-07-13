package com.brodygaudel.bankoperationmanagement.mapping.implementations;

import com.brodygaudel.bankoperationmanagement.dtos.OperationDTO;
import com.brodygaudel.bankoperationmanagement.entities.Operation;
import com.brodygaudel.bankoperationmanagement.mapping.Mappers;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappersImpl implements Mappers {
    @Override
    public OperationDTO fromOperation(@NotNull Operation operation) {
        return new OperationDTO(
                operation.getId(),
                operation.getAmount(),
                operation.getType(),
                operation.getDescription(),
                operation.getDate(),
                operation.getAccountId()
        );
    }

    @Override
    public List<OperationDTO> fromListOfOperations(@NotNull List<Operation> operations) {
        return operations.stream().map(this::fromOperation).toList();
    }
}
