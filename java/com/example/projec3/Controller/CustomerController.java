package com.example.projec3.Controller;

import com.example.projec3.ApiResponse.ApiResponse;
import com.example.projec3.DTO.CustomerDTOin;
import com.example.projec3.DTO.EmployeeDTOin;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get")
    public ResponseEntity getAllCustomer(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(customerService.getAllCustomer(myUser.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTOin customerDTOin){
        customerService.registerCustomer(customerDTOin);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Hello to our web"));}

    @PutMapping("/update/{customer_id}")
    public ResponseEntity updateCustomer(@PathVariable Integer customer_id, @RequestBody @Valid CustomerDTOin customerDTOin) {
        customerService.updateCustomer(customer_id, customerDTOin);
        return ResponseEntity.status(200).body(new ApiResponse("Customer Updated"));
    }



    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer customer_id) {
      customerService.deleteCustomer(myUser.getId(),customer_id);
        return ResponseEntity.status(200).body(new ApiResponse("Customer with ID: " + customer_id + " has been deleted successfully"));
    }
}
