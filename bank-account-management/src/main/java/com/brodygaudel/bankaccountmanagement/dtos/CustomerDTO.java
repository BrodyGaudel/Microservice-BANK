package com.brodygaudel.bankaccountmanagement.dtos;

import com.brodygaudel.bankaccountmanagement.enums.Sex;

import java.util.Date;

public record CustomerDTO(Long id,
                          String firstname,
                          String name,
                          Date dateOfBirth,
                          String placeOfBirth,
                          String nationality,
                          String cin,
                          Sex sex) { }
