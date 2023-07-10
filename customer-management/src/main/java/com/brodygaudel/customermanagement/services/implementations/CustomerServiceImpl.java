package com.brodygaudel.customermanagement.services.implementations;

import com.brodygaudel.customermanagement.dtos.CustomerDTO;
import com.brodygaudel.customermanagement.entities.Customer;
import com.brodygaudel.customermanagement.excpetions.CinAlreadyExistException;
import com.brodygaudel.customermanagement.excpetions.CustomerNotFoundException;
import com.brodygaudel.customermanagement.excpetions.EmailAlreadyExistException;
import com.brodygaudel.customermanagement.excpetions.PhoneAlreadyExistException;
import com.brodygaudel.customermanagement.mapping.Mappers;
import com.brodygaudel.customermanagement.repositories.CustomerRepository;
import com.brodygaudel.customermanagement.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * this class implements all methods defined in CustomerService Interface
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_NOT_FOUND = "customer not found";

    private final CustomerRepository customerRepository;
    private final Mappers mappers;

    public CustomerServiceImpl(CustomerRepository customerRepository, Mappers mappers) {
        this.customerRepository = customerRepository;
        this.mappers = mappers;
    }

    /**
     * Retrieve a customer by his identifier.
     *
     * @param id The identifier of the customer to retrieve.
     * @return The DTO (Data Transfer Object) representing the customer (CustomerDTO)
     * @throws CustomerNotFoundException If the customer is not found.
     */
    @Override
    public CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow( () -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        log.info("customer found");
        return mappers.fromCustomer(customer);
    }

    /**
     * Search for a customer by their identity card number.
     *
     * @param cin The customer's ID card number to search for.
     * @return The DTO (Data Transfer Object) representing the customer found, or null if no client is found.
     */
    @Override
    public CustomerDTO searchCustomerByCin(String cin) {
        Customer customer = customerRepository.findByCin(cin);
        if (customer == null){
            log.info(CUSTOMER_NOT_FOUND);
            return null;
        }
        log.info("customer found");
        return mappers.fromCustomer(customer);
    }

    /**
     * Search for customers based on a keyword.
     *
     * @param keyword The keyword to use for the search.
     * @return A list of DTOs (Data Transfer Objects) representing the customers matching the keyword.
     */
    @Override
    public List<CustomerDTO> searchCustomersByKeyword(String keyword) {
        List<Customer> customers = customerRepository.search("%"+keyword+"%");
        log.info("return all customers matching this keyword");
        return mappers.fromListOfCustomers(customers);
    }

    /**
     * Retrieves all customers.
     *
     * @return A list of DTOs (Data Transfer Objects) representing all clients.
     */
    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        log.info("return all customers");
        return mappers.fromListOfCustomers(customers);
    }

    /**
     * Register a new customer.
     *
     * @param customerDTO The DTO (Data Transfer Object) representing the customer to be registered.
     * @return The DTO (Data Transfer Object) representing the registered customer.
     * @throws CinAlreadyExistException if cin already exist
     * @throws PhoneAlreadyExistException if phone already exist
     * @throws EmailAlreadyExistException if email already exist
     */
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        Customer customer = mappers.fromCustomerDTO(customerDTO);
        checkIfEmailCinPhoneAlreadyExist(customer);
        customer.setDateOfLastUpdate(null);
        customer.setDateOfCreation(new Date());
        Customer savedCustomer = customerRepository.save(customer);
        log.info("customer saved");
        return mappers.fromCustomer(savedCustomer);
    }

    /**
     * Updates an existing client.
     *
     * @param id          The customer ID to be updated.
     * @param customerDTO The DTO (Data Transfer Object) represents the customer's new information.
     * @return The DTO (Data Transfer Object) representing the updated customer.
     * @throws CustomerNotFoundException If the customer is not found.
     * @throws CinAlreadyExistException if cin already exists
     * @throws PhoneAlreadyExistException if phone already exists.
     * @throws EmailAlreadyExistException if email already exists
     */
    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) throws CustomerNotFoundException, CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        Customer customerRetried = customerRepository.findById(id)
                .orElseThrow( () -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        Customer customer = mappers.fromCustomerDTO(customerDTO);
        customer.setId(customerRetried.getId());
        customer.setDateOfCreation(customerRetried.getDateOfCreation());
        customer.setDateOfLastUpdate(new Date());
        checkIfEmailPhoneOrCinChangedTestIfTheyExist(customerRetried, customer);
        Customer updatedCustomer = customerRepository.save(customer);
        log.info("customer updated");
        return mappers.fromCustomer(updatedCustomer);
    }

    /**
     * Deletes a customer by identifier.
     *
     * @param id The identifier of the customer to be deleted.
     */
    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
        log.info("customer deleted");
    }

    /**
     * Check if a customer with the same e-mail address, CIN number or telephone number already exists.
     *
     * @param customer Customer to verify.
     * @throws CinAlreadyExistException     If a customer with the same CIN number already exists.
     * @throws EmailAlreadyExistException   If a customer with the same e-mail address already exists.
     * @throws PhoneAlreadyExistException   If a customer with the same phone number already exists.
     */
    private void checkIfEmailCinPhoneAlreadyExist(@NotNull Customer customer) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        if (Boolean.TRUE.equals(customerRepository.checkIfCinExists(customer.getCin()))) {
            throw new CinAlreadyExistException("There is already a registered customer with this cin");
        }
        if (Boolean.TRUE.equals(customerRepository.checkIfEmailExists(customer.getEmail()))) {
            throw new EmailAlreadyExistException("There is already a registered customer with this email");
        }
        if (Boolean.TRUE.equals(customerRepository.checkIfPhoneExists(customer.getPhone()))) {
            throw new PhoneAlreadyExistException("There is already a registered customer with this phone");
        }
    }

    /**
     * if there are changes on the phone, email and cin: test if they do not already exist
     * @param oldCustomer customer in database
     * @param newCustomer new customer
     * @throws CinAlreadyExistException if cin already exists
     * @throws EmailAlreadyExistException if email already exists
     * @throws PhoneAlreadyExistException if phone already exists
     */
    private void checkIfEmailPhoneOrCinChangedTestIfTheyExist(@NotNull Customer oldCustomer, @NotNull Customer newCustomer) throws CinAlreadyExistException, EmailAlreadyExistException, PhoneAlreadyExistException {
        if (!newCustomer.getCin().equals(oldCustomer.getCin()) && Boolean.TRUE.equals((customerRepository.checkIfCinExists(newCustomer.getCin())))){
            throw new CinAlreadyExistException("There is already a registered customer with this cin");
        }
        if (!newCustomer.getEmail().equals(oldCustomer.getEmail()) && Boolean.TRUE.equals((customerRepository.checkIfEmailExists(newCustomer.getEmail())))){
            throw new EmailAlreadyExistException("There is already a registered customer with this email");
        }
        if(!newCustomer.getPhone().equals(oldCustomer.getPhone()) && Boolean.TRUE.equals((customerRepository.checkIfPhoneExists(newCustomer.getPhone())))){
            throw new PhoneAlreadyExistException("There is already a registered customer with this phone");
        }
    }
}
