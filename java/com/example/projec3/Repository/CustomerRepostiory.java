package com.example.projec3.Repository;

import com.example.projec3.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepostiory extends JpaRepository<Customer,Integer> {
    Customer findCustomerById(Integer id);
}
