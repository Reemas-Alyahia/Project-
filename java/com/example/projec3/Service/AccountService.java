package com.example.projec3.Service;

import com.example.projec3.ApiResponse.ApiException;
import com.example.projec3.DTO.AccountDTO;
import com.example.projec3.Model.Account;
import com.example.projec3.Model.Customer;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Repository.AccountRepository;
import com.example.projec3.Repository.AuthRepsitory;
import com.example.projec3.Repository.CustomerRepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AuthRepsitory authRepsitory;
    private final AccountRepository accountRepository;
    private final CustomerRepostiory customerRepostiory;

    public List<AccountDTO>myAccount(Integer customerId){
        Customer customer=customerRepostiory.findCustomerById(customerId);
        if(customer==null){
            throw new ApiException("Cannot found Customer with this ID"+customerId);
        }
        List<Account>accounts=accountRepository.findAccountsByCustomer(customer);
        if(accounts.isEmpty()){
            throw new ApiException("There is no Account yet");
        }
        List<AccountDTO>accountDTOS=new ArrayList<>();
        for(Account a:accounts){
            accountDTOS.add(new AccountDTO(a.getAccountNumber(),a.getBalance(),a.getIsActive()));
        }
        return accountDTOS;
    }

    /// List user's accounts
    public List<Account>ListUserAccounts(Integer auth){
        MyUser myUser=authRepsitory.findMyUserById(auth);
        if(myUser==null){
          throw new ApiException("Cannot found User with this ID"+auth);
        }
        if(!myUser.getRole().equalsIgnoreCase("ADMIN")|| !myUser.getRole().equalsIgnoreCase("EMPLOYEE")){
           throw new ApiException("Sorry , You do not have the authority to see this Account");
       }
        return accountRepository.findAll();
    }
    /// add new Bank Account
  public void newBankAccount(Account account, Integer auth ){
      Customer customer=customerRepostiory.findCustomerById(auth);
      if(customer==null){
          throw new ApiException("User id not found");
      }
       account.setCustomer(customer);
      accountRepository.save(account);
  }

  /// Update Account
  public void updateAccount(Integer customerId, Integer accountId, Account account) {
      Customer customer = customerRepostiory.findCustomerById(customerId);
      if (customer == null){
          throw new ApiException("Customer was not found");}

      Account oldAccount = accountRepository.findAllByIdAndCustomer(accountId, customer);
      if (oldAccount == null){
          throw new ApiException("Account was not found");}

      oldAccount.setBalance(account.getBalance());
      accountRepository.save(oldAccount);
  }
     ///Active a bank account
    public void activeABankAccount(Integer admin_id,Integer account_id){
        MyUser myUser=authRepsitory.findMyUserById(admin_id);
        if(myUser==null||!myUser.getRole().equalsIgnoreCase("ADMIN")){
            throw new ApiException("Sorry , You do not have the authority to see this Account");
        }
        Account account=accountRepository.findAccountsById(account_id);
        if(account==null){
            throw new ApiException("Account not found");
        }
        account.setIsActive(true);
        accountRepository.save(account);  }


    /// View account details
   public Account viewAccountDetail(Integer account_id,Integer authId) {
       Customer customer = customerRepostiory.findCustomerById(authId);
       if (customer == null) {
           throw new ApiException("Sorry , You do not have the authority to see this Account");
       }
       Account account = accountRepository.findAccountsById(account_id);
       if (account == null) {
           throw new ApiException("No account found");
       }

       if(account.getCustomer().getId()!=authId){
      throw new ApiException("Sorry , You do not have the authority to get this Blog");

       }
     return account;
   }

///  Deposit and withdraw money
/// depositMoney
public void depositMoney(Integer account_id,Double amount){
    Account account=accountRepository.findAccountsById(account_id);
    if(account==null){
        throw new ApiException("Cannot found Account with this ID"+account_id);
    }
    if (!account.getIsActive()){
        throw new ApiException("Your account is not active");}

    if(account.getCustomer().getId()!=account_id){
        throw new ApiException("Sorry , You do not have the authority to see this Account");
    }

    account.setBalance(account.getBalance()+amount);
    accountRepository.save(account);

}

///  withdrawMoney
    public void withdrawMoney(Integer account_id,Double amount){
        Account account=accountRepository.findAccountsById(account_id);
        if(account==null){
            throw new ApiException("Cannot found Account with this ID"+account_id);
        }
        if (!account.getIsActive()){
            throw new ApiException("Your account is not active");}
        if(account.getCustomer().getId()!=account_id){
            throw new ApiException("Sorry , You do not have the authority to see this Account");
        }
        if(account.getBalance()>=amount){
            throw new ApiException(" you don't have money");//******
        }
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);

    }


///Transfer funds between accounts


    public void transferBetweenAccounts(Integer firstAccount,Integer secAccount,Double amount){
     Account account11=accountRepository.findAccountsById(firstAccount);
     if (!account11.getIsActive()){
         throw new ApiException("Your account is not active");}
     if(account11.getCustomer().getId()!=firstAccount){
         throw new ApiException("Sorry , You do not have the authority to do this transfer");
     }
     Account account22=accountRepository.findAccountsById(secAccount);
     if (!account22.getIsActive()){
         throw new ApiException("Your account is not active");}
     if(account11.getBalance()<amount){
         throw new ApiException("");
     }

     account11.setBalance(account11.getBalance()-amount);
     accountRepository.save(account11);

     account22.setBalance(account22.getBalance()+amount);
     accountRepository.save(account22);

 }
 /// Block bank account
 public void blockAccount(Integer admin_id,Integer account_id){
     MyUser myUser=authRepsitory.findMyUserById(admin_id);
     if(myUser==null||!myUser.getRole().equalsIgnoreCase("ADMIN")){
         throw new ApiException("Sorry , You do not have the authority to see this Account");
     }
     Account account=accountRepository.findAccountsById(account_id);
     if(account==null){
         throw new ApiException("Account not found");
     }
     account.setIsActive(false);

     accountRepository.save(account);
 }
/// Delete Account
public void deleteAccount(Integer customerId, Integer accountId) {
    Customer customer = customerRepostiory.findCustomerById(customerId);
    if (customer == null){
        throw new ApiException("Customer was not found");}

    Account account = accountRepository.findAllByIdAndCustomer(accountId, customer);
    if (account == null){
        throw new ApiException("Account was not found");}

    accountRepository.delete(account);
}

}
