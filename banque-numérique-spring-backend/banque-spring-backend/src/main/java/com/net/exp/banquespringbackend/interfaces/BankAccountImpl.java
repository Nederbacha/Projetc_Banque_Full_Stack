package com.net.exp.banquespringbackend.interfaces;

import com.net.exp.banquespringbackend.dtos.*;
import com.net.exp.banquespringbackend.exceptions.BalenceNotSuffisante;
import com.net.exp.banquespringbackend.exceptions.BankAccountNotFoundException;
import com.net.exp.banquespringbackend.exceptions.CustomerRuntimeException;

import java.util.List;

public interface    BankAccountImpl {


    CurrentBankAccountDTO saveCurrenteAccount(double initialBalance, double overDraft, Long customerId) throws CustomerRuntimeException;

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    SavingBankAccountDTO saveSavingAccount(double initialBalance, double interestedRate, Long customerId) throws CustomerRuntimeException;

    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalenceNotSuffisante;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalenceNotSuffisante;


    List<BankAccountDTO>  bankAccountList();

    CustomerDTO getCustomer(Long idCustomer) throws CustomerRuntimeException;

    List<CustomerDTO> rechercheParNameCustomers(String name);

    CustomerDTO ajouterCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long idCustomer);

    List<AccountOperationDTO> getlisteAccountOperDTO(String idBankAccount);

    AccountHistoryDTO getAccountHistory(String idbankAcc, int pages, int size) throws BankAccountNotFoundException;
}
