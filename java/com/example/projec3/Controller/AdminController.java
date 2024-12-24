package com.example.projec3.Controller;

import com.example.projec3.ApiResponse.ApiResponse;
import com.example.projec3.DTO.CustomerDTOin;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
private final AuthService authService;

    @PostMapping("/registerAdmin")
    public ResponseEntity registerAdmin(@RequestBody @Valid MyUser myUser){
        authService.register(myUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Hello to our web"));

    }

//    @PostMapping("/registerCu")
//    public ResponseEntity registerCustomer(@RequestBody @Valid CustomerDTOin customerDTOin){
//        authService.registerCustomer(customerDTOin);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Hello to our web"));
//
//    }
}
