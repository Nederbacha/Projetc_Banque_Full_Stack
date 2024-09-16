package com.net.exp.banquespringbackend.web;

import com.mysql.cj.log.Log;
import com.net.exp.banquespringbackend.dtos.CustomerDTO;
import com.net.exp.banquespringbackend.entities.Customer;
import com.net.exp.banquespringbackend.exceptions.CustomerRuntimeException;
import com.net.exp.banquespringbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customres")
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomersRestController {

    private BankAccountService bankAccountService ;

    @GetMapping("/customers")
    public List<CustomerDTO>  getListeCostumer(){
        log.info("####getListeCostumer");
        return bankAccountService.listCustomers();
    }


    @GetMapping("/customersBy/{keyword}")
    public List<CustomerDTO> getCustomerByName(@PathVariable String keyword){

        return bankAccountService.rechercheParNameCustomers("%"+keyword+"%") ;

    }



    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name ="id") Long customerId) throws CustomerRuntimeException {

    return bankAccountService.getCustomer(customerId);

    }

    @PostMapping("/add")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO  customerDTO  ){
         return bankAccountService.ajouterCustomer(customerDTO) ;
    }

    @PutMapping("/updateCustomer/{idCustomer}")
    public CustomerDTO updateCustomerDTO(@RequestBody CustomerDTO customerDTO , @PathVariable(name ="idCustomer") Long idCustomer){
        customerDTO.setId(idCustomer);
     return  bankAccountService.updateCustomer(customerDTO);
    }


    @DeleteMapping("/delete/{id}")
    public void  deleteCustomer(@PathVariable(name = "id") Long idCustomer){
        bankAccountService.deleteCustomer(idCustomer);
    }
}
