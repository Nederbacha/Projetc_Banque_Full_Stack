package com.net.exp.banquespringbackend.services;

import com.net.exp.banquespringbackend.dtos.*;
import com.net.exp.banquespringbackend.entities.*;
import com.net.exp.banquespringbackend.enums.OperationType;
import com.net.exp.banquespringbackend.exceptions.BalenceNotSuffisante;
import com.net.exp.banquespringbackend.exceptions.BankAccountNotFoundException;
import com.net.exp.banquespringbackend.exceptions.CustomerRuntimeException;
import com.net.exp.banquespringbackend.interfaces.BankAccountImpl;
import com.net.exp.banquespringbackend.mappers.BankAccountMapperImpl;
import com.net.exp.banquespringbackend.repository.AccountOperationRepository;
import com.net.exp.banquespringbackend.repository.BankAccountRepository;
import com.net.exp.banquespringbackend.repository.CustomrRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountService implements BankAccountImpl {

    private AccountOperationRepository accountOperationRepository ;
    private BankAccountRepository bankAccountRepository ;
    private CustomrRepository customrRepository ;
 private BankAccountMapperImpl bankAccountMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("djshkjsdhkfs");

        Customer customer =bankAccountMapper.fromCustomerDTO(customerDTO);
Customer savedCustomer =customrRepository.save(customer) ;

return bankAccountMapper.fromCustomer(savedCustomer);


    }



    @Override
    public SavingBankAccountDTO saveSavingAccount(double initialBalance, double interestedRate, Long customerId) throws CustomerRuntimeException {


        Customer customer = customrRepository.findById(customerId).orElse(null) ;

        SavingAccount savingAccount = new SavingAccount();

        if(customer == null)
            throw  new CustomerRuntimeException("Customer not Existe");
        else{

            savingAccount.setId(UUID.randomUUID().toString());
            savingAccount.setCreatedAt(new Date());
            savingAccount.setCustomer(customer);
            savingAccount.setBalance(initialBalance);
            savingAccount.setInterestRate(interestedRate);

            SavingAccount account=bankAccountRepository.save(savingAccount);
            return bankAccountMapper.fromSavingAccount(account);
        }
    }

    @Override
    public CurrentBankAccountDTO saveCurrenteAccount(double initialBalance, double overDraft, Long customerId) throws CustomerRuntimeException {
        Customer customer = customrRepository.findById(customerId).orElse(null) ;
  if(customer == null)
            throw  new CustomerRuntimeException("Customer not Existe");

            CurrentAccount currentAccount = new CurrentAccount();

            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setCreatedAt(new Date());
            currentAccount.setCustomer(customer);
            currentAccount.setBalance(initialBalance);
            currentAccount.setOverDraft(overDraft);

            CurrentAccount saveBankAccount=bankAccountRepository.save(currentAccount);


            return bankAccountMapper.fromCurrentAccount(saveBankAccount);



    }

    @Override
    public List<CustomerDTO> listCustomers() {

        List<Customer> customers=customrRepository.findAll() ;
        // On applique le programmation fonctionnelle

        return customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());

    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("the account is not existed") );

      if(bankAccount instanceof SavingAccount){

          SavingAccount savingAccount= (SavingAccount) bankAccount ;
          return bankAccountMapper.fromSavingAccount(savingAccount);
      }else{

      //    if(bankAccount instanceof  CurrentAccount){
              CurrentAccount account= (CurrentAccount) bankAccount;
              return bankAccountMapper.fromCurrentAccount(account);

      }


    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalenceNotSuffisante {

        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("the account is not existed") );

        if(bankAccount.getBalance() < amount)
            throw new BalenceNotSuffisante("Balance not suffisante");

        AccountOperation accountOperation = new AccountOperation() ;
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setId(Long.valueOf(accountId));
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);

        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {

        BankAccount bankAccount =bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("the account is not existed") );


        AccountOperation accountOperation = new AccountOperation() ;
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setId(Long.valueOf(UUID.randomUUID().toString()));
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);

        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalenceNotSuffisante {

  debit(accountIdSource , amount ,"Transfer to :"+accountIdDestination );
  credit(accountIdDestination , amount , "Transfer from "+accountIdSource);
    }


    @Override
    public List<BankAccountDTO>  bankAccountList(){

        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        List<BankAccountDTO> listeBankAccountDTOS = bankAccounts.stream().map(bankAcc -> {
            if (bankAcc instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAcc;
                return bankAccountMapper.fromSavingAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAcc;
                return bankAccountMapper.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());

        return  listeBankAccountDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long idCustomer) throws CustomerRuntimeException {

        Customer customer = new Customer() ;
       customer=customrRepository.findById(idCustomer).orElseThrow(()-> new CustomerRuntimeException("Customer not Found"));


       return bankAccountMapper.fromCustomer(customer);

    }

@Override
        public List<CustomerDTO> rechercheParNameCustomers(String name){

     List<Customer> customers=   customrRepository.searchCustomerByName(name) ;

        return customers.stream().map(cast->bankAccountMapper.fromCustomer(cast)).collect(Collectors.toList());
    }

@Override
    public CustomerDTO ajouterCustomer(CustomerDTO customerDTO){

      return saveCustomer(customerDTO) ;

    }
@Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO){

  Customer customer=bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer  customerSaved=customrRepository.save(customer);

        return bankAccountMapper.fromCustomer(customerSaved);


    }

@Override
    public void deleteCustomer(Long idCustomer)    {
 customrRepository.deleteById(idCustomer);

 }


 @Override
 public List<AccountOperationDTO> getlisteAccountOperDTO(String idBankAccount){
     List<AccountOperation> listeOperation = accountOperationRepository.findByBankAccount_Id(idBankAccount);
     return  listeOperation.stream().map(op->bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
 }

@Override
    public AccountHistoryDTO getAccountHistory(String idbankAcc, int pages, int size) throws BankAccountNotFoundException {


        BankAccount account=bankAccountRepository.findById(idbankAcc).orElse(null);

        if(account == null) throw new BankAccountNotFoundException("Account is not Found");

    Page<AccountOperation>  accountOperations = accountOperationRepository.findByBankAccount_Id(idbankAcc, PageRequest.of(pages, size));

AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();

    List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> bankAccountMapper.fromAccountOperation(op)).collect(Collectors.toList());

    accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
    accountHistoryDTO.setAccountId(account.getId());
    accountHistoryDTO.setBalance(account.getBalance());
    accountHistoryDTO.setBalance(account.getBalance());
    accountHistoryDTO.setPageSize(size);
    accountHistoryDTO.setCurrentPage(pages);
    accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

    return accountHistoryDTO;
    }
}
