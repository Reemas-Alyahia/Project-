package com.example.projec3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTOin {

    @NotEmpty(message = "Customer Username cannot be empty")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    private String username;

    @NotEmpty(message = "Customer Password cannot be empty")
    @Size(min = 6, max = 255, message = "User Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "Customer Name cannot be empty")
    @Size(min = 2, max = 20, message = "User Name must be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "Customer Email cannot be empty")
    @Email(message = "Customer Email must be a valid email format")
    private String email;


  
    @NotEmpty(message = "Employee Position cannot be empty")
    private String position;


    @Positive(message = "Employee Salary must be a Positive number")
    private Double salary;
}
