package com.net.exp.banquespringbackend;

import com.net.exp.banquespringbackend.entities.AccountOperation;
import com.net.exp.banquespringbackend.entities.CurrentAccount;
import com.net.exp.banquespringbackend.entities.Customer;
import com.net.exp.banquespringbackend.entities.SavingAccount;
import com.net.exp.banquespringbackend.enums.AccountStatus;
import com.net.exp.banquespringbackend.enums.OperationType;
import com.net.exp.banquespringbackend.repository.AccountOperationRepository;
import com.net.exp.banquespringbackend.repository.BankAccountRepository;
import com.net.exp.banquespringbackend.repository.CustomrRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BanqueSpringBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanqueSpringBackendApplication.class, args);
	}


	@Bean
	CommandLineRunner start(CustomrRepository customrRepository ,
							AccountOperationRepository accountOperationRepository ,
							BankAccountRepository bankAccountRepository){

		return args -> {
			Stream.of("Hassen" , "Yassin" , "Aicha").forEach(name->{
				Customer costumer = new Customer() ;
				costumer.setName(name);
				costumer.setEmail(name + "@gmail.com");
				customrRepository.save(costumer);
			});
			customrRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount=new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount=new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);

			});
			bankAccountRepository.findAll().forEach(acc->{
				for (int i = 0; i <10 ; i++) {
					AccountOperation accountOperation=new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);
				}

			});

		};
	}

}
