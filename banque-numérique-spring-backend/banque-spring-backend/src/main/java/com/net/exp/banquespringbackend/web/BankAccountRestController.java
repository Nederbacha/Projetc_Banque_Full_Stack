package com.net.exp.banquespringbackend.web;

import com.net.exp.banquespringbackend.dtos.*;
import com.net.exp.banquespringbackend.exceptions.BalenceNotSuffisante;
import com.net.exp.banquespringbackend.exceptions.BankAccountNotFoundException;
import com.net.exp.banquespringbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@RequestMapping("/accounts")
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {

    private BankAccountService bankAccountService  ;

     @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable(name = "id") String idAccount) throws BankAccountNotFoundException {
        return   bankAccountService.getBankAccount(idAccount);
    }


    @GetMapping("/accounts")
    public List<BankAccountDTO> listsAccount(){

         return bankAccountService.bankAccountList();
    }


    @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationDTO>  getListOperation(@PathVariable(name = "id") String accountId){

         return bankAccountService.getlisteAccountOperDTO(accountId);

    }

    @GetMapping("/accounts/{id}/pageOperations")
    public AccountHistoryDTO AccountHistory(@PathVariable(name ="id") String  idbankAcc ,
                                                     @RequestParam(name = "size" , defaultValue = "5") int size ,
                                                     @RequestParam(name = "page", defaultValue = "1") int pages) throws BankAccountNotFoundException {


      return  bankAccountService.getAccountHistory(idbankAcc,pages , size) ;

    }



    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO ) throws BankAccountNotFoundException, BalenceNotSuffisante {
         bankAccountService.debit(debitDTO.getAccountID() ,debitDTO.getAmount() , debitDTO.getDescription());
         return debitDTO ;
    }


    @PostMapping("/accounts/credit")
// String accountId, double amount, String description)
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
         bankAccountService.credit(creditDTO.getAccountID() , creditDTO.getAmount() , creditDTO.getDescription());
         return creditDTO ;
    }
// String accountIdSource, String accountIdDestination, double amount


    @PostMapping("/accounts/transfer")
    public void transferReqDTO(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalenceNotSuffisante {

         bankAccountService.transfer(transferRequestDTO.getAccountSource() , transferRequestDTO.getAccountDestination() , transferRequestDTO.getAmount());



    }
}
