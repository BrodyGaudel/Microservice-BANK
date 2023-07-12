package com.brodygaudel.bankaccountmanagement.mapping.implementations;

import com.brodygaudel.bankaccountmanagement.dtos.CurrentBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.SavingBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.entities.CurrentBankAccount;
import com.brodygaudel.bankaccountmanagement.entities.SavingBankAccount;
import com.brodygaudel.bankaccountmanagement.mapping.Mappers;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class MappersImpl implements Mappers {

    /**
     * Converts a SavingBankAccount object to a SavingBankAccountDTO object.
     *
     * @param savingBankAccount the SavingBankAccount object to be converted
     * @return the SavingBankAccountDTO object representing the converted SavingBankAccount
     */
    @Override
    public SavingBankAccountDTO fromSavingBankAccount(@NotNull SavingBankAccount savingBankAccount) {
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();

        savingBankAccountDTO.setId(savingBankAccount.getId());
        savingBankAccountDTO.setAmount(savingBankAccount.getAmount());
        savingBankAccountDTO.setCurrency(savingBankAccount.getCurrency());
        savingBankAccountDTO.setCustomerId(savingBankAccount.getCustomerId());
        savingBankAccountDTO.setStatus(savingBankAccount.getStatus());
        savingBankAccountDTO.setDateOfCreation(savingBankAccount.getDateOfCreation());
        savingBankAccountDTO.setDateOfLastUpdate(savingBankAccount.getDateOfLastUpdate());
        savingBankAccountDTO.setType(savingBankAccount.getClass().getSimpleName());
        savingBankAccountDTO.setInterestRate(savingBankAccount.getInterestRate());

        return savingBankAccountDTO;
    }

    /**
     * Converts a SavingBankAccountDTO object to a SavingBankAccount object.
     *
     * @param savingBankAccountDTO the SavingBankAccountDTO object to be converted
     * @return the SavingBankAccount object representing the converted SavingBankAccountDTO
     */
    @Override
    public SavingBankAccount fromSavingBankAccountDTO(@NotNull SavingBankAccountDTO savingBankAccountDTO) {
        SavingBankAccount savingBankAccount = new SavingBankAccount();
        savingBankAccount.setInterestRate(savingBankAccountDTO.getInterestRate());
        savingBankAccount.setId(savingBankAccountDTO.getId());
        savingBankAccount.setCurrency(savingBankAccountDTO.getCurrency());
        savingBankAccount.setCustomerId(savingBankAccountDTO.getCustomerId());
        savingBankAccount.setDateOfCreation(savingBankAccountDTO.getDateOfCreation());
        savingBankAccount.setAmount(savingBankAccountDTO.getAmount());
        savingBankAccount.setDateOfLastUpdate(savingBankAccountDTO.getDateOfLastUpdate());
        savingBankAccount.setStatus(savingBankAccountDTO.getStatus());
        return savingBankAccount;
    }

    /**
     * Converts a CurrentBankAccount object to a CurrentBankAccountDTO object.
     *
     * @param currentBankAccount the CurrentBankAccount object to be converted
     * @return the CurrentBankAccountDTO object representing the converted CurrentBankAccount
     */
    @Override
    public CurrentBankAccountDTO fromCurrentBankAccount(@NotNull CurrentBankAccount currentBankAccount) {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();

        currentBankAccountDTO.setId(currentBankAccount.getId());
        currentBankAccountDTO.setAmount(currentBankAccount.getAmount());
        currentBankAccountDTO.setCurrency(currentBankAccount.getCurrency());
        currentBankAccountDTO.setStatus(currentBankAccount.getStatus());
        currentBankAccountDTO.setDateOfCreation(currentBankAccount.getDateOfCreation());
        currentBankAccountDTO.setDateOfLastUpdate(currentBankAccount.getDateOfLastUpdate());
        currentBankAccountDTO.setOverDraft(currentBankAccount.getOverDraft());
        currentBankAccountDTO.setType(currentBankAccount.getClass().getSimpleName());
        currentBankAccountDTO.setCustomerId(currentBankAccount.getCustomerId());

        return currentBankAccountDTO;
    }

    /**
     * Converts a CurrentBankAccountDTO object to a CurrentBankAccount object.
     *
     * @param currentBankAccountDTO the CurrentBankAccountDTO object to be converted
     * @return the CurrentBankAccount object representing the converted CurrentBankAccountDTO
     */
    @Override
    public CurrentBankAccount fromCurrentBankAccountDTO(@NotNull CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentBankAccount currentBankAccount = new CurrentBankAccount();
        currentBankAccount.setCurrency(currentBankAccountDTO.getCurrency());
        currentBankAccount.setId(currentBankAccountDTO.getId());
        currentBankAccount.setStatus(currentBankAccountDTO.getStatus());
        currentBankAccount.setCustomerId(currentBankAccountDTO.getCustomerId());
        currentBankAccount.setAmount(currentBankAccountDTO.getAmount());
        currentBankAccount.setDateOfCreation(currentBankAccountDTO.getDateOfCreation());
        currentBankAccount.setDateOfLastUpdate(currentBankAccountDTO.getDateOfLastUpdate());
        currentBankAccount.setOverDraft(currentBankAccountDTO.getOverDraft());
        return currentBankAccount;
    }

}
