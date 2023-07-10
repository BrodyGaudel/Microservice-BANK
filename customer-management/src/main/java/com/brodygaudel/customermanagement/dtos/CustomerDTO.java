package com.brodygaudel.customermanagement.dtos;

import com.brodygaudel.customermanagement.enums.Sex;

import java.util.Date;

public record CustomerDTO(Long id,
                          String firstname,
                          String name,
                          String placeOfBirth,
                          Date dateOfBirth,
                          String nationality,
                          Sex sex,
                          String email,
                          String phone,
                          String cin,
                          Date dateOfCreation,
                          Date dateOfLastUpdate) {
}
