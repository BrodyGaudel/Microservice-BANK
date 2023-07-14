# E-BANK

E-BANK is a banking application developed using microservices architecture, Java (17), Spring Boot, Spring Cloud, Maven, and MySQL. It consists of three microservices: Customer Management, Account Management, and Transaction Management. The application also includes a Discovery service using Netflix Eureka Discovery and a Gateway service.

## Microservices

### Customer Management Microservice

This microservice handles the management of bank customers. It provides endpoints for creating, retrieving, updating, and deleting customer information. It is responsible for maintaining customer data and ensuring its consistency.

### Account Management Microservice

The Account Management microservice is responsible for managing bank accounts for customers. It provides functionalities to create new accounts, retrieve account details, update account information, and delete accounts. It ensures the integrity and security of account-related operations.

### Transaction Management Microservice

The Transaction Management microservice handles all banking operations, such as credit, debit, and transfers between accounts. It provides endpoints to initiate transactions, retrieve transaction history, and generate transaction reports. It ensures the accuracy and consistency of financial transactions.

### Discovery Service

The Discovery service, implemented using Netflix Eureka Discovery, facilitates service discovery and registration. It allows microservices to locate and communicate with each other dynamically. This enables seamless integration and scalability within the E-BANK application.

### Gateway Service

The Gateway service acts as an entry point for external requests. It handles authentication, authorization, and routing of incoming requests to the appropriate microservices. The Gateway provides a secure and centralized access point to the E-BANK application.

## Prerequisites

Make sure you have the following dependencies installed:

- Java 17
- Spring Boot
- Spring Cloud
- Maven
- MySQL

## Getting Started

1. Clone the E-BANK repository.

```
git clone https://github.com/yourusername/e-bank.git
```

2. Configure the database settings in the `application.properties` file of each microservice.

```
# Customer Management Microservice
spring.datasource.url=jdbc:mysql://localhost:3306/e_bank_customer
spring.datasource.username=root
spring.datasource.password=password

# Account Management Microservice
spring.datasource.url=jdbc:mysql://localhost:3306/e_bank_account
spring.datasource.username=root
spring.datasource.password=password

# Transaction Management Microservice
spring.datasource.url=jdbc:mysql://localhost:3306/e_bank_transaction
spring.datasource.username=root
spring.datasource.password=password
```

3. Build the microservices using Maven.

```
cd e-bank
mvn clean install
```

4. Start the microservices in the following order:

- Discovery Service
- Gateway Service
- Customer Management Microservice
- Account Management Microservice
- Transaction Management Microservice

```
cd discovery-service
mvn spring-boot:run

cd gateway-service
mvn spring-boot:run

cd customer-management-service
mvn spring-boot:run

cd account-management-service
mvn spring-boot:run

cd transaction-management-service
mvn spring-boot:run
```

5. Access the E-BANK application through the Gateway service using the specified endpoints.

## Contributing

Contributions to E-BANK are welcome! If you find any issues or have suggestions for improvement, please create a GitHub issue or submit a pull request.

## License

The E-BANK application is open-source and released under the [MIT License](https://opensource.org/licenses/MIT). Feel free to use, modify, and distribute it as per the terms of the license.

## Contact

For any inquiries or additional information, please contact the E-BANK development team at ebank@example.com.
