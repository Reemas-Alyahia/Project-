package com.example.projec3.Repository;

import com.example.projec3.Model.Account;
import com.example.projec3.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {

    Account findAccountsById(Integer id);

  Account findAllByIdAndCustomer(Integer id,Customer customer);
 List<Account>findAccountsByCustomer(Customer customer);
}
