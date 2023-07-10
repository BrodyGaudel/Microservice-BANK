package com.brodygaudel.customermanagement.mapping;

import com.brodygaudel.customermanagement.dtos.CustomerDTO;
import com.brodygaudel.customermanagement.entities.Customer;

import java.util.List;

/**
 * This interface defines methods for mapping between different object types.
 * Each method in this interface represents a specific mapper for a particular mapping.
 */
public interface Mappers {

    /**
     * map a CustomerDTO object to a Customer object.
     * @param customerDTO the CustomerDTO object to map
     * @return the Customer object resulting from the mapping
     */
    Customer fromCustomerDTO(CustomerDTO customerDTO);

    /**
     * map a Customer object to a CustomerDTO object.
     * @param customer the Customer object to map
     * @return the CustomerDTO object resulting from the mapping
     */
    CustomerDTO fromCustomer(Customer customer);

    /**
     * map a list of Customer's objects to a list of CustomerDTOs objects
     * @param customers the list of customer's objects to map
     * @return the list of CustomerDTOs objects resulting from the mapping
     */
    List<CustomerDTO> fromListOfCustomers(List<Customer> customers);
}
