
package com.brodygaudel.customermanagement.services;

import com.brodygaudel.customermanagement.dtos.CustomerDTO;
import com.brodygaudel.customermanagement.excpetions.CinAlreadyExistException;
import com.brodygaudel.customermanagement.excpetions.CustomerNotFoundException;
import com.brodygaudel.customermanagement.excpetions.EmailAlreadyExistException;
import com.brodygaudel.customermanagement.excpetions.PhoneAlreadyExistException;

import java.util.List;

/**
 * Interface representing the customer management service.
 */
public interface CustomerService {

    /**
     * Retrieve a customer by his identifier.
     * @param id The identifier of the customer to retrieve.
     * @return The DTO (Data Transfer Object) representing the customer (CustomerDTO)
     * @throws CustomerNotFoundException If the customer is not found.
     */
    CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException;

    /**
     * Search for a customer by their identity card number.
     * @param cin The customer's ID card number to search for.
     * @return The DTO (Data Transfer Object) representing the customer found, or null if no client is found.
     */
    CustomerDTO searchCustomerByCin(String cin);

    /**
     * Search for customers based on a keyword.
     * @param keyword The keyword to use for the search.
     * @return A list of DTOs (Data Transfer Objects) representing the customers matching the keyword.
     */
    List<CustomerDTO> searchCustomersByKeyword(String keyword);

    /**
     * Retrieves all customers.
     * @return A list of DTOs (Data Transfer Objects) representing all clients.
     */
    List<CustomerDTO> getAllCustomers();

    /**
     * Register a new customer.
     *
     * @param customerDTO The DTO (Data Transfer Object) representing the customer to be registered.
     * @return The DTO (Data Transfer Object) representing the registered customer.
     * @throws CinAlreadyExistException if customer with same cin already exist
     * @throws PhoneAlreadyExistException if customer with same phone already exist
     * @throws EmailAlreadyExistException if customer with same email already exist
     */
    CustomerDTO saveCustomer(CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException;

    /**
     * Updates an existing client.
     *
     * @param id          The customer ID to be updated.
     * @param customerDTO The DTO (Data Transfer Object) represents the customer's new information.
     * @return The DTO (Data Transfer Object) representing the updated customer.
     * @throws CustomerNotFoundException If the customer is not found.
     * @throws CinAlreadyExistException if customer with same cin already exists
     * @throws PhoneAlreadyExistException if customer with same phone already exists.
     * @throws EmailAlreadyExistException if customer with same email already exists
     */
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException;

    /**
     * Deletes a customer by identifier.
     * @param id The identifier of the customer to be deleted.
     */
    void deleteCustomerById(Long id);
}
