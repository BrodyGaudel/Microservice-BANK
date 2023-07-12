package com.brodygaudel.bankaccountmanagement.restcontrollers;

import com.brodygaudel.bankaccountmanagement.dtos.BankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.CurrentBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.dtos.Form;
import com.brodygaudel.bankaccountmanagement.dtos.SavingBankAccountDTO;
import com.brodygaudel.bankaccountmanagement.exceptions.BankAccountNotFoundException;
import com.brodygaudel.bankaccountmanagement.exceptions.CustomerNotFoundException;
import com.brodygaudel.bankaccountmanagement.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class BankAccountRestController {

    private final BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/get/{id}")
    public BankAccountDTO getBankAccountById(@PathVariable String id) throws BankAccountNotFoundException{
        return bankAccountService.getBankAccountById(id);
    }

    @GetMapping("/list")
    public List<BankAccountDTO> getAllBankAccounts(){
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping("/all/{customerId}")
    public List<BankAccountDTO> getBankAccountsByCustomerId(@PathVariable Long customerId){
        return bankAccountService.getBankAccountsByCustomerId(customerId);
    }

    @PostMapping("/create/current")
    public BankAccountDTO createCurrentBankAccount(@RequestBody CurrentBankAccountDTO currentBankAccountDTO) throws CustomerNotFoundException{
        return bankAccountService.createCurrentBankAccount(currentBankAccountDTO);
    }

    @PostMapping("/create/saving")
    public BankAccountDTO createSavingBankAccount(@RequestBody SavingBankAccountDTO savingBankAccountDTO) throws CustomerNotFoundException{
        return bankAccountService.createSavingBankAccount(savingBankAccountDTO);
    }

    @PutMapping("/update/{id}")
    public BankAccountDTO updateBankAccount(@PathVariable String id, @RequestBody Form form) throws BankAccountNotFoundException{
        return bankAccountService.updateBankAccount(id, form);
    }
}
