package com.net.exp.banquespringbackend.repository;

import com.net.exp.banquespringbackend.dtos.CustomerDTO;
import com.net.exp.banquespringbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface CustomrRepository extends JpaRepository<Customer ,Long> {


      @Query("select c from Customer c where  c.name like :kw")
      List<Customer>  searchCustomerByName(@Param(value = "kw") String kw) ;

   //  List<Customer> searchCustomerByName(String keyword);


}
