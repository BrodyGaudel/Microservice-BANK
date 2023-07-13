package com.brodygaudel.bankoperationmanagement.services;

import com.brodygaudel.bankoperationmanagement.dtos.*;
import com.brodygaudel.bankoperationmanagement.exceptions.BalanceNotSufficientException;
import com.brodygaudel.bankoperationmanagement.exceptions.BankAccountNotActivatedException;
import com.brodygaudel.bankoperationmanagement.exceptions.BankAccountNotFoundException;
import com.brodygaudel.bankoperationmanagement.exceptions.OperationNotFoundException;

import java.util.List;

public interface OperationService {
    /**
     * debit a bank account
     * @param debitDTO basic information to debit a bank account
     * @return basic information for a bank account debited
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to debit does not exist.
     * @throws BalanceNotSufficientException raises this exception if balance not sufficient
     * @throws BankAccountNotActivatedException raises this exception if bank account's status is not activated
     */
    DebitDTO debit(DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException;

    /**
     * credit a bank account
     * @param creditDTO basic information to credit a bank account
     * @return basic information of bank account credited
     * @throws BankAccountNotFoundException raises this exception if the bank account you want to credit does not exist.
     * @throws BankAccountNotActivatedException raises this exception if bank account status is not activated
     */
    CreditDTO credit(CreditDTO creditDTO) throws BankAccountNotFoundException, BankAccountNotActivatedException;

    /**
     * make a transfer from a bank account to another bank account
     * @param transferDTO basic information to make a transfer
     * @return basic information of transfer done
     * @throws BankAccountNotFoundException raises this exception if the bank account(s) you want to transfer does not exist.
     * @throws BalanceNotSufficientException raises this exception if balance not sufficient to make a transfer
     * @throws BankAccountNotActivatedException raises this exception if bank account status is not activated
     */
    TransferDTO transfer(TransferDTO transferDTO) throws BankAccountNotFoundException, BalanceNotSufficientException, BankAccountNotActivatedException;

    /**
     * get all operations by account id
     * @param accountId id of bank account that you want to retrieve operations
     * @return a list of AccountOperation where accountId equal id
     */
    List<OperationDTO> getAllOperationsByAccountId(String accountId);

    /**
     * get AccountOperation by id
     * @param id the id of AccountOperation you want to get
     * @return AccountOperation found
     * @throws OperationNotFoundException raises this exception if AccountOperation not found
     */
    OperationDTO getOperationById(String id) throws OperationNotFoundException;

    /**
     * get the transaction history (credit, debit) of a bank account.
     * @param accountId the id of the bank account whose history you want to read
     * @param page the number of a page you want to get
     * @param size the size of a page
     * @return bank account history
     * @throws BankAccountNotFoundException raises this exception if the bank account whose history you want to consult does not exist.
     */
    HistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
