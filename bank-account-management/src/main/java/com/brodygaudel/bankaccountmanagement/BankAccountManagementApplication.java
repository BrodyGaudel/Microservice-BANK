package com.brodygaudel.bankaccountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BankAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAccountManagementApplication.class, args);
	}

}
