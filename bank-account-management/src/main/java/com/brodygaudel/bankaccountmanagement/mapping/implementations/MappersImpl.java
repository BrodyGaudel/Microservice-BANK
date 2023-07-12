package com.brodygaudel.bankaccountmanagement.mapping.implementations;

import com.brodygaudel.bankaccountmanagement.dtos.BankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.CurrentBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.SavingBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.entities.BankAccount;
import com.brodygaudel.bankaccountmanagement.entities.CurrentBankAccount;
import com.brodygaudel.bankaccountmanagement.entities.SavingBankAccount;
import com.brodygaudel.bankaccountmanagement.mapping.Mappers;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

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
        SavingBankAccountDTO savingBankAccountDTO = SavingBankAccountDTO.builder()
                .id(savingBankAccount.getId())
                .amount(savingBankAccount.getAmount())
                .currency(savingBankAccount.getCurrency())
                .status(savingBankAccount.getStatus())
                .dateOfCreation(savingBankAccount.getDateOfCreation())
                .dateOfLastUpdate(savingBankAccount.getDateOfLastUpdate())
                .interestRate(savingBankAccount.getInterestRate())
                .build();
        savingBankAccountDTO.setType(savingBankAccount.getClass().getSimpleName());
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
        CurrentBankAccountDTO currentBankAccountDTO = CurrentBankAccountDTO.builder()
                .id(currentBankAccount.getId())
                .amount(currentBankAccount.getAmount())
                .currency(currentBankAccount.getCurrency())
                .status(currentBankAccount.getStatus())
                .dateOfCreation(currentBankAccount.getDateOfCreation())
                .dateOfLastUpdate(currentBankAccount.getDateOfLastUpdate())
                .overDraft(currentBankAccount.getOverDraft())
                .build();
        currentBankAccountDTO.setType(currentBankAccount.getClass().getSimpleName());
        return null;
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

    /**
     * Converts list of BankAccount object to a list of BankAccountDTO object.
     *
     * @param bankAccounts list of BankAccount object to be converted
     * @return list of BankAccountDTO converted
     */
    @Override
    public List<BankAccountDTO> fromListOfBankAccounts(@NotNull List<BankAccount> bankAccounts) {
        return bankAccounts.stream().map(this::fromBankAccount).toList();
    }

    /**
     * map BankAccount to BankAccountDTO
     *
     * @param bankAccount BankAccount to be mapped
     * @return bankAccountDTO mapped
     */
    @Override
    public BankAccountDTO fromBankAccount(BankAccount bankAccount) {
        if (bankAccount instanceof SavingBankAccount savingBankAccount) {
            return fromSavingBankAccount(savingBankAccount);
        } else if (bankAccount instanceof CurrentBankAccount currentBankAccount) {
            return fromCurrentBankAccount(currentBankAccount);
        }else {
            throw new IllegalArgumentException("Unsupported Bank Account Type");
        }
    }
}
