package com.brodygaudel.customermanagement.mapping.implementations;

import com.brodygaudel.customermanagement.dtos.CustomerDTO;
import com.brodygaudel.customermanagement.entities.Customer;
import com.brodygaudel.customermanagement.mapping.Mappers;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class implements methods for mapping between different object types.
 * Each method in this class represents a specific mapper for a particular mapping.
 */
@Service
public class MappersImpl implements Mappers {

    /**
     * map a CustomerDTO object to a Customer object.
     *
     * @param customerDTO the CustomerDTO object to map
     * @return the Customer object resulting from the mapping
     */
    @Override
    public Customer fromCustomerDTO(@NotNull CustomerDTO customerDTO) {
        return Customer.builder()
                .id(customerDTO.id())
                .firstname(customerDTO.firstname())
                .name(customerDTO.name())
                .placeOfBirth(customerDTO.placeOfBirth())
                .dateOfBirth(customerDTO.dateOfBirth())
                .nationality(customerDTO.nationality())
                .sex(customerDTO.sex())
                .email(customerDTO.email())
                .phone(customerDTO.phone())
                .cin(customerDTO.cin())
                .dateOfCreation(customerDTO.dateOfCreation())
                .dateOfLastUpdate(customerDTO.dateOfLastUpdate())
                .build();
    }

    /**
     * map a Customer object to a CustomerDTO object.
     *
     * @param customer the Customer object to map
     * @return the CustomerDTO object resulting from the mapping
     */
    @Override
    public CustomerDTO fromCustomer(@NotNull Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstname(),
                customer.getName(),
                customer.getPlaceOfBirth(),
                customer.getDateOfBirth(),
                customer.getNationality(),
                customer.getSex(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCin(),
                customer.getDateOfCreation(),
                customer.getDateOfLastUpdate()
        );
    }

    /**
     * map a list of Customer's objects to a list of CustomerDTOs objects
     *
     * @param customers the list of customer's objects to map
     * @return the list of CustomerDTOs objects resulting from the mapping
     */
    @Override
    public List<CustomerDTO> fromListOfCustomers(@NotNull List<Customer> customers) {
        return customers.stream().map(this::fromCustomer).toList();
    }
}
