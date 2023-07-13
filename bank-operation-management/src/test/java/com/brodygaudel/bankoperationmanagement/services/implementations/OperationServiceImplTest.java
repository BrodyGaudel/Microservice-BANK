package com.brodygaudel.bankoperationmanagement.services.implementations;

import com.brodygaudel.bankoperationmanagement.dtos.*;
import com.brodygaudel.bankoperationmanagement.entities.Operation;
import com.brodygaudel.bankoperationmanagement.enums.Currency;
import com.brodygaudel.bankoperationmanagement.enums.Type;
import com.brodygaudel.bankoperationmanagement.exceptions.BalanceNotSufficientException;
import com.brodygaudel.bankoperationmanagement.exceptions.BankAccountNotActivatedException;
import com.brodygaudel.bankoperationmanagement.exceptions.BankAccountNotFoundException;
import com.brodygaudel.bankoperationmanagement.exceptions.OperationNotFoundException;
import com.brodygaudel.bankoperationmanagement.mapping.implementations.MappersImpl;
import com.brodygaudel.bankoperationmanagement.repositories.OperationRepository;
import com.brodygaudel.bankoperationmanagement.services.feign.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OperationServiceImplTest {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private BankAccountService bankAccountService;
    private OperationServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new OperationServiceImpl(operationRepository, new MappersImpl(), bankAccountService);
    }

    @Test
    void testDebit() throws BankAccountNotFoundException, BankAccountNotActivatedException, BalanceNotSufficientException {
        DebitDTO debitDTO = new DebitDTO("10000012023", BigDecimal.valueOf(5), "test");
        DebitDTO dto = service.debit(debitDTO);
        assertNotNull(dto);
    }

    @Test
    void testCredit() throws BankAccountNotFoundException, BankAccountNotActivatedException {
        CreditDTO creditDTO = new CreditDTO("10000012023", BigDecimal.valueOf(5000000), "test");
        CreditDTO dto = service.credit(creditDTO);
        assertNotNull(dto);
    }

    @Test
    void testTransfer() throws BankAccountNotFoundException, BankAccountNotActivatedException, BalanceNotSufficientException {
        TransferDTO transferDTO = new TransferDTO("10000012023", "10000012023", BigDecimal.valueOf(3), "test");
        TransferDTO dto = service.transfer(transferDTO);
        assertNotNull(dto);
    }

    @Test
    void testGetAllOperationsByAccountId() {
        Operation operation = Operation.builder()
                .id(UUID.randomUUID().toString())
                .type(Type.CREDIT)
                .date(new Date())
                .currency(String.valueOf(Currency.CAD))
                .description("test")
                .accountId("10000012023")
                .amount(BigDecimal.valueOf(10000))
                .build();
        Operation savedOperation = operationRepository.save(operation);
        List<OperationDTO> operations = service.getAllOperationsByAccountId("10000012023");
        assertNotNull(operations);
        assertFalse(operations.isEmpty());
        OperationDTO operationDTO = operations.get(0);
        assertNotNull(operationDTO);
        assertEquals(operationDTO.accountId(), savedOperation.getAccountId());
    }

    @Test
    void testGetOperationById() throws OperationNotFoundException {
        Operation operation = Operation.builder()
                .id(UUID.randomUUID().toString())
                .type(Type.CREDIT)
                .date(new Date())
                .currency(String.valueOf(Currency.CAD))
                .description("test")
                .accountId("10000012023")
                .amount(BigDecimal.valueOf(10000))
                .build();
        Operation savedOperation = operationRepository.save(operation);
        OperationDTO operationDTO = service.getOperationById(savedOperation.getId());
        assertNotNull(operationDTO);
        assertEquals(operationDTO.accountId(), savedOperation.getAccountId());
        assertEquals(operationDTO.id(), savedOperation.getId());
        assertEquals(operationDTO.date(), savedOperation.getDate());
    }

    @Test
    void testGetAccountHistory() throws BankAccountNotFoundException {
        Operation operation = Operation.builder()
                .id(UUID.randomUUID().toString())
                .type(Type.CREDIT)
                .date(new Date())
                .currency(String.valueOf(Currency.CAD))
                .description("test")
                .accountId("10000012023")
                .amount(BigDecimal.valueOf(10000))
                .build();
        Operation savedOperation = operationRepository.save(operation);
        HistoryDTO historyDTO = service.getAccountHistory(savedOperation.getAccountId(), 0, 5);
        assertNotNull(historyDTO);
        assertFalse(historyDTO.operationDTOS().isEmpty());
        OperationDTO operationDTO = historyDTO.operationDTOS().get(0);
        assertNotNull(operationDTO);
        assertEquals(operationDTO.accountId(), savedOperation.getAccountId());
    }
}