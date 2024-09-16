package com.net.exp.banquespringbackend.entities;


import com.net.exp.banquespringbackend.enums.AccountStatus;
import com.net.exp.banquespringbackend.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import  lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;
    private String description;


}
