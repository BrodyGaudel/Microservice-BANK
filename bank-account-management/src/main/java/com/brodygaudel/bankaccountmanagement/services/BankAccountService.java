package com.brodygaudel.bankaccountmanagement.services;

import com.brodygaudel.bankaccountmanagement.dtos.BankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.CurrentBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.Form;
import com.brodygaudel.bankaccountmanagement.dtos.SavingBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.exceptions.BankAccountNotFoundException;
import com.brodygaudel.bankaccountmanagement.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    /**
     * Retrieves a bank account by its ID.
     *
     * @param id the ID of the bank account
     * @return the BankAccountDTO object representing the bank account
     */
    BankAccountDTO getBankAccountById(String id) throws BankAccountNotFoundException;

    /**
     * Retrieves all bank accounts.
     *
     * @return a list of BankAccountDTO objects representing the bank accounts
     */
    List<BankAccountDTO> getAllBankAccounts();

    /**
     * Retrieves bank accounts associated with a specific customer ID.
     *
     * @param id the ID of the customer
     * @return a list of BankAccountDTO objects representing the bank accounts
     */
    List<BankAccountDTO> getBankAccountsByCustomerId(Long id);

    /**
     * Creates a new current bank account.
     *
     * @param currentBankAccountDTO the CurrentBankAccountDTO object containing the details of the current bank account
     * @return the BankAccountDTO object representing the newly created bank account
     */
    BankAccountDTO createCurrentBankAccount(CurrentBankAccountDTO currentBankAccountDTO) throws CustomerNotFoundException;

    /**
     * Creates a new saving bank account.
     *
     * @param savingBankAccountDTO the SavingBankAccountDTO object containing the details of the saving bank account
     * @return the BankAccountDTO object representing the newly created bank account
     */
    BankAccountDTO createSavingBankAccount(SavingBankAccountDTO savingBankAccountDTO) throws CustomerNotFoundException;

    /**
     * Updates an existing bank account.
     *
     * @param id the ID of the bank account to be updated
     * @param form the Form object containing the updated information
     * @return the BankAccountDTO object representing the updated bank account
     */
    BankAccountDTO updateBankAccount(String id, Form form) throws BankAccountNotFoundException;
}

