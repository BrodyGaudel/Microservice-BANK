package com.brodygaudel.customermanagement.restcontrollers;

import com.brodygaudel.customermanagement.dtos.CustomerDTO;
import com.brodygaudel.customermanagement.excpetions.CinAlreadyExistException;
import com.brodygaudel.customermanagement.excpetions.CustomerNotFoundException;
import com.brodygaudel.customermanagement.excpetions.EmailAlreadyExistException;
import com.brodygaudel.customermanagement.excpetions.PhoneAlreadyExistException;
import com.brodygaudel.customermanagement.services.CustomerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class represents a REST controller for managing customer-related operations.
 * It handles HTTP requests for customer data and delegates the processing to the CustomerService.
 * The base path for all endpoints in this controller is "/v1/customers".
 */
@RestController
@RequestMapping("/v1/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer to retrieve
     * @return the CustomerDTO representing the customer with the specified ID
     * @throws CustomerNotFoundException if the customer with the specified ID is not found
     */
    @GetMapping("/get/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) throws CustomerNotFoundException{
        return customerService.getCustomerById(id);
    }

    /**
     * Searches for a customer by their CIN (Customer Identification Number).
     *
     * @param cin the CIN of the customer to search for
     * @return the CustomerDTO representing the customer matching the specified CIN
     */
    @GetMapping("/find")
    public CustomerDTO searchCustomerByCin(@RequestParam(name = "cin", defaultValue = "") String cin){
        return customerService.searchCustomerByCin(cin);
    }

    /**
     * Searches for customers by a keyword.
     *
     * @param keyword the keyword to search for in customer information
     * @return a list of CustomerDTO objects representing the customers matching the keyword
     */
    @GetMapping("/search")
    public List<CustomerDTO> searchCustomersByKeyword(@RequestParam(name = "keyword", defaultValue = "") String keyword){
        return customerService.searchCustomersByKeyword(keyword);
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of CustomerDTO objects representing all customers
     */
    @GetMapping("/list")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    /**
     * Saves a new customer.
     *
     * @param customerDTO the CustomerDTO object representing the customer to be saved
     * @return the CustomerDTO representing the saved customer
     * @throws CinAlreadyExistException if a customer with the same CIN already exists
     * @throws EmailAlreadyExistException if a customer with the same email already exists
     * @throws PhoneAlreadyExistException if a customer with the same phone number already exists
     */
    @PostMapping("/save")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException{
        return customerService.saveCustomer(customerDTO);
    }

    /**
     * Updates an existing customer.
     *
     * @param id the ID of the customer to update
     * @param customerDTO the CustomerDTO object representing the updated customer data
     * @return the CustomerDTO representing the updated customer
     * @throws CustomerNotFoundException if the customer with the specified ID is not found
     * @throws CinAlreadyExistException if a customer with the same CIN already exists
     * @throws EmailAlreadyExistException if a customer with the same email already exists
     * @throws PhoneAlreadyExistException if a customer with the same phone number already exists
     */
    @PutMapping("/update/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException{
        return customerService.updateCustomer(id, customerDTO);
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param id the ID of the customer to delete
     */
    @DeleteMapping("/delete/{id}")
    public void deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
    }

    /**
     * Handles exceptions thrown during request processing.
     *
     * @param exception the exception that occurred during request processing
     * @return a ResponseEntity with an error message and HTTP status code indicating the error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(@NotNull Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
