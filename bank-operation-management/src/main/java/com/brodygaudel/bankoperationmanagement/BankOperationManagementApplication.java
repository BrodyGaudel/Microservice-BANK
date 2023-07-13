package com.brodygaudel.bankoperationmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BankOperationManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankOperationManagementApplication.class, args);
	}

}
