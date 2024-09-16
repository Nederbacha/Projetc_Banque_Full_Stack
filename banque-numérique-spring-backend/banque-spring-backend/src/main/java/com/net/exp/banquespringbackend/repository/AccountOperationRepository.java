package com.net.exp.banquespringbackend.repository;

import com.net.exp.banquespringbackend.entities.AccountOperation;
import com.net.exp.banquespringbackend.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

    public  List<AccountOperation> findByBankAccount_Id(String accountId) ;

    public Page<AccountOperation>  findByBankAccount_Id(String accountOperation, Pageable pageable);




}
