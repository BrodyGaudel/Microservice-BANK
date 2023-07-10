package com.brodygaudel.customermanagement.entities;

import com.brodygaudel.customermanagement.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String name;
    private String placeOfBirth;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String nationality;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(unique = true, nullable = false)
    private String cin;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfCreation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfLastUpdate;
}
