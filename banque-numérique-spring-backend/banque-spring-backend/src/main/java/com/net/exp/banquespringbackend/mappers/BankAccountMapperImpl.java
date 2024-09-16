package com.net.exp.banquespringbackend.mappers;

import com.net.exp.banquespringbackend.dtos.AccountOperationDTO;
import com.net.exp.banquespringbackend.dtos.CurrentBankAccountDTO;
import com.net.exp.banquespringbackend.dtos.CustomerDTO;
import com.net.exp.banquespringbackend.dtos.SavingBankAccountDTO;
import com.net.exp.banquespringbackend.entities.AccountOperation;
import com.net.exp.banquespringbackend.entities.CurrentAccount;
import com.net.exp.banquespringbackend.entities.Customer;
import com.net.exp.banquespringbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service

public class BankAccountMapperImpl {

public CustomerDTO fromCustomer(Customer customer){

    CustomerDTO customerDTO = new CustomerDTO();
    BeanUtils.copyProperties(customer , customerDTO);

    return customerDTO ;
}


    public Customer fromCustomerDTO(CustomerDTO customerDTO){
    Customer customer  = new Customer();
    BeanUtils.copyProperties(customerDTO , customer);
    return customer ;
    }
public SavingBankAccountDTO fromSavingAccount(SavingAccount savingAccount){

    SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO() ;
    BeanUtils.copyProperties(savingAccount , savingBankAccountDTO);
    savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
    savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
    return savingBankAccountDTO ;
}

public SavingAccount fromSavingAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
    SavingAccount savingAccount = new SavingAccount() ;
    BeanUtils.copyProperties(savingBankAccountDTO , savingAccount);
    savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
    return savingAccount ;

}

public CurrentAccount fromCurrentAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){
    CurrentAccount account = new CurrentAccount() ;
    BeanUtils.copyProperties(currentBankAccountDTO , account);
    account.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
     return account ;
}



public CurrentBankAccountDTO fromCurrentAccount(CurrentAccount currentAccount){
    CurrentBankAccountDTO bankAccountDTO=new CurrentBankAccountDTO() ;
    BeanUtils.copyProperties(currentAccount ,bankAccountDTO);
    bankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
    bankAccountDTO.setType(currentAccount.getClass().getSimpleName());
    return bankAccountDTO ;

}



public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){

    AccountOperationDTO accountOperationDTO= new AccountOperationDTO() ;
    BeanUtils.copyProperties(accountOperation,accountOperationDTO);
return accountOperationDTO ;
}

public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO){

    AccountOperation accountOperation=new AccountOperation() ;
    BeanUtils.copyProperties(accountOperationDTO , accountOperation);
    return accountOperation ;

}
}
