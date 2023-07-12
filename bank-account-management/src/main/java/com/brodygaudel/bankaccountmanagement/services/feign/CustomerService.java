package com.brodygaudel.bankaccountmanagement.services.feign;

import com.brodygaudel.bankaccountmanagement.dtos.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerService {

    @GetMapping("/e-bank/api/v1/customers/get/{id}")
    CustomerDTO getCustomerById(@PathVariable Long id);
}
