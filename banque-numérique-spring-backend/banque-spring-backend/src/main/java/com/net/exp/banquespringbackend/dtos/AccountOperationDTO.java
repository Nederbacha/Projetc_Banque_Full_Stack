package com.net.exp.banquespringbackend.dtos;


import com.net.exp.banquespringbackend.entities.BankAccount;
import com.net.exp.banquespringbackend.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;

    private OperationType type;

    private String description;


}
