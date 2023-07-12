package com.brodygaudel.bankaccountmanagement.services.implementations;

import com.brodygaudel.bankaccountmanagement.dtos.*;
import com.brodygaudel.bankaccountmanagement.entities.BankAccount;
import com.brodygaudel.bankaccountmanagement.entities.Compter;
import com.brodygaudel.bankaccountmanagement.entities.CurrentBankAccount;
import com.brodygaudel.bankaccountmanagement.entities.SavingBankAccount;
import com.brodygaudel.bankaccountmanagement.enums.Status;
import com.brodygaudel.bankaccountmanagement.exceptions.BankAccountNotFoundException;
import com.brodygaudel.bankaccountmanagement.exceptions.CustomerNotFoundException;
import com.brodygaudel.bankaccountmanagement.mapping.Mappers;
import com.brodygaudel.bankaccountmanagement.repositories.BankAccountRepository;
import com.brodygaudel.bankaccountmanagement.repositories.CompterRepository;
import com.brodygaudel.bankaccountmanagement.services.BankAccountService;
import com.brodygaudel.bankaccountmanagement.services.feign.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final Mappers mappers;
    private final CompterRepository compterRepository;
    private final CustomerService customerService;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, Mappers mappers, CompterRepository compterRepository, CustomerService customerService) {
        this.bankAccountRepository = bankAccountRepository;
        this.mappers = mappers;
        this.compterRepository = compterRepository;
        this.customerService = customerService;
    }

    /**
     * Retrieves a bank account by its ID.
     *
     * @param id the ID of the bank account
     * @return the BankAccountDTO object representing the bank account
     */
    @Override
    public BankAccountDTO getBankAccountById(String id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow( () -> new BankAccountNotFoundException("Bank Account Not Found"));
        log.info("bank account found");
        if (bankAccount instanceof SavingBankAccount savingBankAccount) {
            return mappers.fromSavingBankAccount(savingBankAccount);
        } else if (bankAccount instanceof CurrentBankAccount currentBankAccount) {
            return mappers.fromCurrentBankAccount(currentBankAccount);
        }else {
            throw new IllegalArgumentException("Unsupported Bank Account Type");
        }
    }

    /**
     * Retrieves all bank accounts.
     *
     * @return a list of BankAccountDTO objects representing the bank accounts
     */
    @Override
    public List<BankAccountDTO> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        log.info("all bank account returned");
        return fromListOfBankAccount(bankAccounts);
    }

    /**
     * Retrieves bank accounts associated with a specific customer ID.
     *
     * @param id the ID of the customer
     * @return a list of BankAccountDTO objects representing the bank accounts
     */
    @Override
    public List<BankAccountDTO> getBankAccountsByCustomerId(Long id) {
        List<BankAccount> bankAccountList = bankAccountRepository.findByCustomerId(id);
        log.info("return all bank accounts for customer with id :"+id);
        return fromListOfBankAccount(bankAccountList);
    }

    /**
     * Creates a new current bank account.
     *
     * @param currentBankAccountDTO the CurrentBankAccountDTO object containing the details of the current bank account
     * @return the BankAccountDTO object representing the newly created bank account
     */
    @Override
    public BankAccountDTO createCurrentBankAccount(CurrentBankAccountDTO currentBankAccountDTO) throws CustomerNotFoundException {
        CurrentBankAccount currentBankAccount = mappers.fromCurrentBankAccountDTO(currentBankAccountDTO);
        CustomerDTO customerDTO = customerService.getCustomerById(currentBankAccount.getCustomerId());
        if(customerDTO == null){
            throw new CustomerNotFoundException("Customer Not Found");
        }
        currentBankAccount.setCustomerId(customerDTO.id());
        currentBankAccount.setStatus(Status.ACTIVATED);
        currentBankAccount.setDateOfLastUpdate(null);
        currentBankAccount.setId(autoGenerate());
        currentBankAccount.setDateOfCreation(new Date());
        CurrentBankAccount bankAccount = bankAccountRepository.save(currentBankAccount);
        log.info("current bank account saved");
        return mappers.fromCurrentBankAccount(bankAccount);
    }

    /**
     * Creates a new saving bank account.
     *
     * @param savingBankAccountDTO the SavingBankAccountDTO object containing the details of the saving bank account
     * @return the BankAccountDTO object representing the newly created bank account
     */
    @Override
    public BankAccountDTO createSavingBankAccount(SavingBankAccountDTO savingBankAccountDTO) throws CustomerNotFoundException {
        SavingBankAccount savingBankAccount = mappers.fromSavingBankAccountDTO(savingBankAccountDTO);
        CustomerDTO customerDTO = customerService.getCustomerById(savingBankAccount.getCustomerId());
        if(customerDTO == null){
            throw new CustomerNotFoundException("Customer Not Found");
        }
        savingBankAccount.setStatus(Status.ACTIVATED);
        savingBankAccount.setDateOfLastUpdate(null);
        savingBankAccount.setId(autoGenerate());
        savingBankAccount.setDateOfCreation(new Date());
        SavingBankAccount bankAccount = bankAccountRepository.save(savingBankAccount);
        log.info("saving bank account saved");
        return mappers.fromSavingBankAccount(bankAccount);
    }

    /**
     * Updates an existing bank account.
     *
     * @param id   the ID of the bank account to be updated
     * @param form the Form object containing the updated information
     * @return the BankAccountDTO object representing the updated bank account
     */
    @Override
    public BankAccountDTO updateBankAccount(String id, @NotNull Form form) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow( () -> new BankAccountNotFoundException("Bank Account Not Found"));
        bankAccount.setAmount(form.balance());
        bankAccount.setStatus(form.status());
        bankAccount.setDateOfLastUpdate(new Date());
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        log.info("bank account updated");
        if (savedBankAccount instanceof SavingBankAccount savingBankAccount) {
            return mappers.fromSavingBankAccount(savingBankAccount);
        } else if (bankAccount instanceof CurrentBankAccount currentBankAccount) {
            return mappers.fromCurrentBankAccount(currentBankAccount);
        }else {
            throw new IllegalArgumentException("Unsupported Bank Account Type");
        }
    }


    /**
     * Bank Account Number Generator
     * @return Bank Account Number as a String
     */
    private @NotNull String autoGenerate(){
        Long lastId = compterRepository.findMaxId().orElse(1000000L);
        Long newId = lastId + 1;
        compterRepository.save(new Compter(newId));
        LocalDate currentDate = LocalDate.now();
        return newId.toString() + currentDate.getYear();
    }

    private List<BankAccountDTO> fromListOfBankAccount(@NotNull List<BankAccount> bankAccounts){
        return bankAccounts.stream()
                .map(bankAccount -> {
                    if(bankAccount instanceof SavingBankAccount savingBankAccount){
                        return mappers.fromSavingBankAccount(savingBankAccount);
                    } else if (bankAccount instanceof CurrentBankAccount currentBankAccount ) {
                        return mappers.fromCurrentBankAccount(currentBankAccount);
                    }else {
                        throw new IllegalArgumentException("Unsupported Bank Account Type :");
                    }
                }).toList();
    }
}
