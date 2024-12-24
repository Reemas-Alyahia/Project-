package com.example.projec3.Controller;

import com.example.projec3.ApiResponse.ApiResponse;
import com.example.projec3.Model.Account;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/getMyAccounts")
    public ResponseEntity getMyAccount(@AuthenticationPrincipal MyUser auth){
        return ResponseEntity.status(200).body(accountService.myAccount(auth.getId()));
    }

    //ListUserAccounts
    @GetMapping("/list-of-account")
    public ResponseEntity ListUserAccounts( @AuthenticationPrincipal MyUser auth){
    return ResponseEntity.status(200).body( accountService.ListUserAccounts(auth.getId()));
    }


    /// add new Bank Account
    @PostMapping("/add-Bank-account")
    public ResponseEntity addNewBank(@RequestBody @Valid Account account, @AuthenticationPrincipal MyUser auth){
        accountService.newBankAccount(account,auth.getId());

        return ResponseEntity.status(200).body(new ApiResponse("Bank Account added"));
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity updateAccount(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId, @RequestBody @Valid Account account) {
        accountService.updateAccount(myUser.getId(), accountId, account);
        return ResponseEntity.status(200).body(new ApiResponse("Account has been updated successfully"));
    }

    @PutMapping("/active/{account_id}")
    public ResponseEntity activeABankAccount(@AuthenticationPrincipal MyUser auth, @PathVariable Integer account_id){
        accountService.activeABankAccount(auth.getId(),account_id);
        return ResponseEntity.status(200).body(new ApiResponse("Done form Active"));
    }


    @GetMapping("/view-account-details/{account_id}")
    public ResponseEntity viewAccountDetail(  @PathVariable Integer account_id, @AuthenticationPrincipal MyUser auth){
        return ResponseEntity.status(200).body( accountService.viewAccountDetail(account_id,auth.getId()));
    }


    @PutMapping("/deposit-Money/{amount}")
    public ResponseEntity depositMoney(@AuthenticationPrincipal MyUser auth, @PathVariable Double amount){
        accountService.depositMoney(auth.getId(),amount);
        return ResponseEntity.status(200).body(new ApiResponse("Done form deposit the money"));
    }

    @PutMapping("/withdraw-Money/{amount}")
    public ResponseEntity withdrawMoney(@AuthenticationPrincipal MyUser auth, @PathVariable Double amount){
        accountService.withdrawMoney(auth.getId(),amount);
        return ResponseEntity.status(200).body(new ApiResponse("Done form withdraw the money"));
    }

    //transferBetweenAccounts

    @PutMapping("/transfer/{account1}/{account2}/{amount}")
    public ResponseEntity<ApiResponse> transferBetweenAccounts(
            @AuthenticationPrincipal MyUser auth,
           @PathVariable Integer account1,
            @PathVariable Integer account2,
            @PathVariable Double amount) {

        accountService.transferBetweenAccounts(account1, account2, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transfer completed successfully"));
    }
    /// Block bank account

    @PutMapping("/blockAccount/{account_id}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal MyUser auth, @PathVariable Integer account_id){
        accountService.blockAccount(auth.getId(),account_id);
        return ResponseEntity.status(200).body(new ApiResponse("Done form Active"));
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer accountId) {
        accountService.deleteAccount(myUser.getId(), accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account has been deleted successfully"));
    }

}
