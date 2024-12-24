package com.example.projec3.Service;

import com.example.projec3.ApiResponse.ApiException;
import com.example.projec3.DTO.AccountDTO;
import com.example.projec3.DTO.CustomerDTO;
import com.example.projec3.DTO.CustomerDTOin;
import com.example.projec3.DTO.EmployeeDTOin;
import com.example.projec3.Model.Account;
import com.example.projec3.Model.Customer;
import com.example.projec3.Model.Employee;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Repository.AuthRepsitory;
import com.example.projec3.Repository.CustomerRepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private final AuthRepsitory authRepsitory;
  private final CustomerRepostiory customerRepostiory;


   public List<CustomerDTO>getAllCustomer(Integer auth){
     MyUser myUser=authRepsitory.findMyUserById(auth);
     if(myUser==null){
         throw new ApiException("Cannot found Account with this ID"+auth);}
     if(myUser.getRole().equalsIgnoreCase("EMPLOYEE")|| myUser.getRole().equalsIgnoreCase("ADMIN")  ){
         List<Customer>customerList=customerRepostiory.findAll();
         if(customerList.isEmpty()){
             throw new ApiException("There no Customer yet");
         }
         List<CustomerDTO>customerDTOS=new ArrayList<>();
         for(Customer cc:customerList){
             List<AccountDTO>accountDTOS=new ArrayList<>();
             for(Account aa:cc.getAccounts())
                 accountDTOS.add(new AccountDTO(aa.getAccountNumber(), aa.getBalance(), aa.getIsActive()));

             customerDTOS.add(new CustomerDTO(cc.getUser().getUsername(),cc.getUser().getEmail()
             ,cc.getUser().getName(),cc.getPhoneNumber(),accountDTOS));
         }
         return customerDTOS;
     }
     else throw new ApiException("Sorry , You do not have the authority to see this Details ");
   }



    public  void registerCustomer(CustomerDTOin customerDTOin){
        MyUser myUser=authRepsitory.findMyUserByUsername(customerDTOin.getUsername());
        if (myUser != null) {
            throw new ApiException("User already exists");
        }

        MyUser myUser1=new MyUser();

        myUser1.setUsername(customerDTOin.getUsername());
        myUser1.setPassword(new BCryptPasswordEncoder().encode(customerDTOin.getPassword()));
        myUser1.setName(customerDTOin.getName());
        myUser1.setEmail(customerDTOin.getEmail());
        myUser1.setRole("CUSTOMER");
        authRepsitory.save(myUser1);

        Customer customer=new Customer();
        customer.setPhoneNumber(customerDTOin.getPhoneNumber());
        customer.setUser(myUser1);


        customerRepostiory.save(customer);
     }

     public void updateCustomer(Integer customer_id,CustomerDTOin customerDTOin){
        Customer customer=customerRepostiory.findCustomerById(customer_id);

        MyUser myUser=customer.getUser();
        myUser.setUsername(customerDTOin.getUsername());
        myUser.setName(customerDTOin.getName());
        myUser.setEmail(customerDTOin.getEmail());
         myUser.setRole(myUser.getRole());

        /// ////
        customer.setPhoneNumber(customer.getPhoneNumber());
        authRepsitory.save(myUser);
        customerRepostiory.save(customer);
     }


    public void deleteCustomer(Integer auth, Integer customerId) {
        MyUser user = authRepsitory.findMyUserById(auth);
        if (user == null){
            throw new ApiException("Cannot found Account with this ID"+auth);}

        MyUser customer = authRepsitory.findMyUserById(customerId);
        if (customer == null){
            throw new ApiException("Cannot found customer with this ID"+customerId);}

        if (auth.equals(customerId) || user.getRole().equals("ADMIN"))
            authRepsitory.delete(customer);
        else throw new ApiException("You Cannot delete this customer");
    }


}





















