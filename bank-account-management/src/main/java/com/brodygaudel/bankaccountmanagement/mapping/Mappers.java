package com.brodygaudel.bankaccountmanagement.mapping;

import com.brodygaudel.bankaccountmanagement.dtos.CurrentBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.SavingBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.entities.CurrentBankAccount;
import com.brodygaudel.bankaccountmanagement.entities.SavingBankAccount;


public interface Mappers {
    /**
     * Converts a SavingBankAccount object to a SavingBankAccountDTO object.
     *
     * @param savingBankAccount the SavingBankAccount object to be converted
     * @return the SavingBankAccountDTO object representing the converted SavingBankAccount
     */
    SavingBankAccountDTO fromSavingBankAccount(SavingBankAccount savingBankAccount);

    /**
     * Converts a SavingBankAccountDTO object to a SavingBankAccount object.
     *
     * @param savingBankAccountDTO the SavingBankAccountDTO object to be converted
     * @return the SavingBankAccount object representing the converted SavingBankAccountDTO
     */
    SavingBankAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO);

    /**
     * Converts a CurrentBankAccount object to a CurrentBankAccountDTO object.
     *
     * @param currentBankAccount the CurrentBankAccount object to be converted
     * @return the CurrentBankAccountDTO object representing the converted CurrentBankAccount
     */
    CurrentBankAccountDTO fromCurrentBankAccount(CurrentBankAccount currentBankAccount);

    /**
     * Converts a CurrentBankAccountDTO object to a CurrentBankAccount object.
     *
     * @param currentBankAccountDTO the CurrentBankAccountDTO object to be converted
     * @return the CurrentBankAccount object representing the converted CurrentBankAccountDTO
     */
    CurrentBankAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO);
}

