package com.brodygaudel.bankoperationmanagement.mapping;

import com.brodygaudel.bankoperationmanagement.dtos.OperationDTO;
import com.brodygaudel.bankoperationmanagement.entities.Operation;

import java.util.List;

public interface Mappers {
    OperationDTO fromOperation(Operation operation);
    List<OperationDTO> fromListOfOperations(List<Operation> operations);
}
