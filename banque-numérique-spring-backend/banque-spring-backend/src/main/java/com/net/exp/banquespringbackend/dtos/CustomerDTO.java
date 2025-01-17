package com.net.exp.banquespringbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.net.exp.banquespringbackend.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String email;

}
