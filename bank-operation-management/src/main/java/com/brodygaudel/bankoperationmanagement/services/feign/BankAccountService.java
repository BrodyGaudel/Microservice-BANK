package com.brodygaudel.bankoperationmanagement.services.feign;

import com.brodygaudel.bankoperationmanagement.dtos.BankAccountDTO;
import com.brodygaudel.bankoperationmanagement.dtos.Form;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface BankAccountService {

    @GetMapping("/e-bank/api/v1/accounts/get/{id}")
    BankAccountDTO getBankAccountById(@PathVariable String id);

    @PutMapping("/e-bank/api/v1/accounts/update/{id}")
    BankAccountDTO updateBankAccount(@PathVariable String id, @RequestBody Form form);
}
