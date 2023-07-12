package com.brodygaudel.bankaccountmanagement.services.implementations;

import com.brodygaudel.bankaccountmanagement.dtos.BankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.CurrentBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.Form;
import com.brodygaudel.bankaccountmanagement.dtos.SavingBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.entities.BankAccount;
import com.brodygaudel.bankaccountmanagement.entities.CurrentBankAccount;
import com.brodygaudel.bankaccountmanagement.enums.Currency;
import com.brodygaudel.bankaccountmanagement.enums.Status;
import com.brodygaudel.bankaccountmanagement.exceptions.BankAccountNotFoundException;
import com.brodygaudel.bankaccountmanagement.exceptions.CustomerNotFoundException;
import com.brodygaudel.bankaccountmanagement.mapping.implementations.MappersImpl;
import com.brodygaudel.bankaccountmanagement.repositories.BankAccountRepository;
import com.brodygaudel.bankaccountmanagement.repositories.CompterRepository;
import com.brodygaudel.bankaccountmanagement.services.feign.CustomerService;
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
class BankAccountServiceImplTest {

    @Autowired
    private BankAccountRepository repository;

    @Autowired
    private CompterRepository compterRepository;

    @Autowired
    private CustomerService customerService;
    private BankAccountServiceImpl bankAccountService;

    @BeforeEach
    void setUp() {
        bankAccountService = new BankAccountServiceImpl(repository,
                new MappersImpl(), compterRepository, customerService );
    }

    @Test
    void getBankAccountById() throws BankAccountNotFoundException {
        CurrentBankAccount bankAccount = new CurrentBankAccount();
        bankAccount.setStatus(Status.ACTIVATED);
        bankAccount.setAmount(BigDecimal.valueOf(8000000));
        bankAccount.setCustomerId(1L);
        bankAccount.setDateOfLastUpdate(null);
        bankAccount.setDateOfCreation(new Date());
        bankAccount.setCurrency(Currency.EUR);
        bankAccount.setId(UUID.randomUUID().toString());
        BankAccount accountSaved = repository.save(bankAccount);
        CurrentBankAccountDTO bankAccountFound = (CurrentBankAccountDTO) bankAccountService.getBankAccountById(accountSaved.getId());
        assertNotNull(bankAccountFound);
        assertEquals(bankAccountFound.getId(), accountSaved.getId());
        assertEquals(bankAccountFound.getCustomerId(), accountSaved.getCustomerId());
    }

    @Test
    void getAllBankAccounts() {
        CurrentBankAccount bankAccount = new CurrentBankAccount();
        bankAccount.setStatus(Status.ACTIVATED);
        bankAccount.setAmount(BigDecimal.valueOf(8000000));
        bankAccount.setCustomerId(1L);
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setDateOfLastUpdate(null);
        bankAccount.setDateOfCreation(new Date());
        bankAccount.setCurrency(Currency.EUR);
        repository.save(bankAccount);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getAllBankAccounts();
        assertNotNull(bankAccountDTOList);
        assertFalse(bankAccountDTOList.isEmpty());
    }

    @Test
    void getBankAccountsByCustomerId() {
        CurrentBankAccount bankAccount = new CurrentBankAccount();
        bankAccount.setStatus(Status.ACTIVATED);
        bankAccount.setAmount(BigDecimal.valueOf(8000000));
        bankAccount.setCustomerId(1L);
        bankAccount.setDateOfLastUpdate(null);
        bankAccount.setDateOfCreation(new Date());
        bankAccount.setCurrency(Currency.EUR);
        bankAccount.setId(UUID.randomUUID().toString());
        repository.save(bankAccount);
        List<BankAccountDTO> bankAccountDTOList = bankAccountService.getBankAccountsByCustomerId(1L);
        assertNotNull(bankAccountDTOList);
        assertFalse(bankAccountDTOList.isEmpty());
    }

    @Test
    void createCurrentBankAccount() throws CustomerNotFoundException {
        CurrentBankAccountDTO bankAccount = new CurrentBankAccountDTO();
        bankAccount.setStatus(Status.ACTIVATED);
        bankAccount.setAmount(BigDecimal.valueOf(8000000));
        bankAccount.setCustomerId(1L);
        bankAccount.setDateOfLastUpdate(null);
        bankAccount.setDateOfCreation(new Date());
        bankAccount.setCurrency(Currency.EUR);
        CurrentBankAccountDTO bankAccountDTO = (CurrentBankAccountDTO) bankAccountService.createCurrentBankAccount(bankAccount);
        assertNotNull(bankAccountDTO);
        assertEquals(bankAccountDTO.getCustomerId(), bankAccount.getCustomerId());
        assertEquals(bankAccountDTO.getCurrency(), bankAccount.getCurrency());
        assertEquals(bankAccountDTO.getAmount(), bankAccount.getAmount());
        assertEquals(bankAccountDTO.getStatus(), bankAccount.getStatus());
    }

    @Test
    void createSavingBankAccount() throws CustomerNotFoundException {
        SavingBankAccountDTO bankAccount = new SavingBankAccountDTO();
        bankAccount.setStatus(Status.ACTIVATED);
        bankAccount.setAmount(BigDecimal.valueOf(8000000));
        bankAccount.setCustomerId(1L);
        bankAccount.setDateOfLastUpdate(null);
        bankAccount.setDateOfCreation(new Date());
        bankAccount.setCurrency(Currency.EUR);
        SavingBankAccountDTO bankAccountDTO = (SavingBankAccountDTO) bankAccountService.createSavingBankAccount(bankAccount);
        assertNotNull(bankAccountDTO);
        assertEquals(bankAccountDTO.getCustomerId(), bankAccount.getCustomerId());
        assertEquals(bankAccountDTO.getCurrency(), bankAccount.getCurrency());
        assertEquals(bankAccountDTO.getAmount(), bankAccount.getAmount());
        assertEquals(bankAccountDTO.getStatus(), bankAccount.getStatus());
    }

    @Test
    void updateBankAccount() throws BankAccountNotFoundException {
        CurrentBankAccount bankAccount = new CurrentBankAccount();
        bankAccount.setStatus(Status.ACTIVATED);
        bankAccount.setAmount(BigDecimal.valueOf(8000000));
        bankAccount.setCustomerId(1L);
        bankAccount.setDateOfLastUpdate(null);
        bankAccount.setDateOfCreation(new Date());
        bankAccount.setCurrency(Currency.EUR);
        bankAccount.setId(UUID.randomUUID().toString());
        BankAccount accountSaved = repository.save(bankAccount);

        Form form = new Form(BigDecimal.valueOf(546), Status.SUSPENDED);
        CurrentBankAccountDTO bankAccountDTO = (CurrentBankAccountDTO) bankAccountService.updateBankAccount(accountSaved.getId(), form);
        assertNotNull(bankAccountDTO);
        assertNotEquals(bankAccountDTO.getAmount(), accountSaved.getAmount());
        assertNotEquals(bankAccountDTO.getStatus(), accountSaved.getStatus());
        assertEquals(bankAccountDTO.getId(), accountSaved.getId());
    }
}