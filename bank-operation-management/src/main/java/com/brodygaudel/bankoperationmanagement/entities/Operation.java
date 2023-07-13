package com.brodygaudel.bankoperationmanagement.entities;

import com.brodygaudel.bankoperationmanagement.enums.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Operation {
    @Id
    private String id;
    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String description;
    private Date date;

    @Column(nullable = false)
    private String accountId;
}
