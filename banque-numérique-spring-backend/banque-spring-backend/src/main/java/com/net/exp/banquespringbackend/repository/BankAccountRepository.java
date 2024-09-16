package com.net.exp.banquespringbackend.repository;

import com.net.exp.banquespringbackend.entities.BankAccount;
import com.net.exp.banquespringbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
