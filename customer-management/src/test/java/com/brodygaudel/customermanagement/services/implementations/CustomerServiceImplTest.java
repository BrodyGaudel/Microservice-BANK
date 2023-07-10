package com.brodygaudel.customermanagement.services.implementations;

import com.brodygaudel.customermanagement.dtos.CustomerDTO;
import com.brodygaudel.customermanagement.entities.Customer;
import com.brodygaudel.customermanagement.enums.Sex;
import com.brodygaudel.customermanagement.excpetions.CinAlreadyExistException;
import com.brodygaudel.customermanagement.excpetions.CustomerNotFoundException;
import com.brodygaudel.customermanagement.excpetions.EmailAlreadyExistException;
import com.brodygaudel.customermanagement.excpetions.PhoneAlreadyExistException;
import com.brodygaudel.customermanagement.mapping.implementations.MappersImpl;
import com.brodygaudel.customermanagement.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceImplTest {

    @Autowired
    private CustomerRepository repository;
    private CustomerServiceImpl service;

    @BeforeEach
    public void setup() {
        service = new CustomerServiceImpl(repository, new MappersImpl());
    }


    @Test
    void getCustomerById() throws CustomerNotFoundException {
        Customer customer = Customer.builder()
                .firstname("John").cin(UUID.randomUUID().toString())
                .phone(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        Customer savedCustomer = repository.save(customer);
        CustomerDTO customerRetrieved = service.getCustomerById(savedCustomer.getId());
        assertNotNull(customerRetrieved);
        assertEquals(savedCustomer.getId(), customerRetrieved.id());
        assertEquals(savedCustomer.getEmail(), customerRetrieved.email());
        assertEquals(savedCustomer.getPhone(), customerRetrieved.phone());
        assertEquals(savedCustomer.getCin(), customerRetrieved.cin());
    }

    @Test
    void searchCustomerByCin() {
        Customer customer = Customer.builder()
                .firstname("John").cin(UUID.randomUUID().toString())
                .phone(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        Customer savedCustomer = repository.save(customer);
        CustomerDTO customerRetrieved = service.searchCustomerByCin(savedCustomer.getCin());
        assertNotNull(customerRetrieved);
        assertEquals(savedCustomer.getId(), customerRetrieved.id());
        assertEquals(savedCustomer.getEmail(), customerRetrieved.email());
        assertEquals(savedCustomer.getPhone(), customerRetrieved.phone());
        assertEquals(savedCustomer.getCin(), customerRetrieved.cin());
    }

    @Test
    void searchCustomersByKeyword() {
        Customer customer = Customer.builder()
                .firstname("John").name("Doe").cin(UUID.randomUUID().toString())
                .phone(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        Customer savedCustomer = repository.save(customer);
        List<CustomerDTO> customerDTOList = service.searchCustomersByKeyword("John");
        assertNotNull(customerDTOList);
        assertFalse(customerDTOList.isEmpty());
        CustomerDTO customerDTO = customerDTOList.get(0);
        assertTrue(customerDTO.firstname().contains(savedCustomer.getFirstname()));
    }

    @Test
    void getAllCustomers() {
        Customer customer = Customer.builder()
                .firstname("John").name("Doe").cin(UUID.randomUUID().toString())
                .phone(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        repository.save(customer);
        List<CustomerDTO> customerDTOList = service.getAllCustomers();
        assertNotNull(customerDTOList);
        assertFalse(customerDTOList.isEmpty());
    }

    @Test
    void saveCustomer() throws PhoneAlreadyExistException, CinAlreadyExistException, EmailAlreadyExistException {
        CustomerDTO customerDTO = new CustomerDTO(null, "John", "Doe",
                "GABON", new Date(), "GABON", Sex.M,
                UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), null, null);
        CustomerDTO customerDTOSaved = service.saveCustomer(customerDTO);
        assertNotNull(customerDTOSaved);
        assertEquals(customerDTOSaved.cin(), customerDTO.cin());
        assertEquals(customerDTOSaved.email(), customerDTO.email());
        assertEquals(customerDTOSaved.phone(), customerDTO.phone());
        assertEquals(customerDTOSaved.sex(), customerDTO.sex());
        assertEquals(customerDTOSaved.firstname(), customerDTO.firstname());
        assertEquals(customerDTOSaved.name(), customerDTO.name());
        assertEquals(customerDTOSaved.nationality(), customerDTO.nationality());
        assertEquals(customerDTOSaved.placeOfBirth(), customerDTO.placeOfBirth());
    }

    @Test
    void updateCustomer() throws PhoneAlreadyExistException, CinAlreadyExistException, CustomerNotFoundException, EmailAlreadyExistException {
        Customer customer = Customer.builder()
                .firstname("John").name("Doe").cin(UUID.randomUUID().toString())
                .phone(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        Customer savedCustomer = repository.save(customer);
        CustomerDTO customerDTO = new CustomerDTO(null, "John", "Doe",
                "GABON", new Date(), "GABON", Sex.M,
                UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), null, null);
        CustomerDTO customerDTOUpdated = service.updateCustomer(savedCustomer.getId(), customerDTO);
        assertNotNull(customerDTOUpdated);
        assertEquals(customerDTOUpdated.id(), savedCustomer.getId());
        assertNotNull(customerDTOUpdated.dateOfLastUpdate());
    }

    @Test
    void deleteCustomerById() {
        Customer customer = Customer.builder()
                .firstname("John").name("Doe").cin(UUID.randomUUID().toString())
                .phone(UUID.randomUUID().toString()).email(UUID.randomUUID().toString())
                .build();
        Customer savedCustomer = repository.save(customer);
        service.deleteCustomerById(savedCustomer.getId());
        Customer customerFound = repository.findById(savedCustomer.getId()).orElse(null);
        assertNull(customerFound);
    }
}