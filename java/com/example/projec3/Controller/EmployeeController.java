package com.example.projec3.Controller;

import com.example.projec3.ApiResponse.ApiResponse;
import com.example.projec3.DTO.CustomerDTOin;
import com.example.projec3.DTO.EmployeeDTOin;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity getAllEmployee(@AuthenticationPrincipal MyUser myUser) {
        return ResponseEntity.status(200).body(employeeService.getAllEmployees(myUser.getId()));
    }


    @PostMapping("/registerEmployee")
    public ResponseEntity registerEmployee(@RequestBody @Valid EmployeeDTOin employeeDTOin){
        employeeService.registerEmployee(employeeDTOin);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Hello to our web"));

    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity updateEmployee(@PathVariable Integer employeeId, @RequestBody @Valid EmployeeDTOin employeeDTOin){
      employeeService.updateEmployee(employeeId, employeeDTOin);
        return ResponseEntity.status(200).body(new ApiResponse("Employee Updated"));

    }


    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer employeeId) {
        employeeService.deleteEmployee(myUser.getId(), employeeId);
        return ResponseEntity.status(200).body(new ApiResponse("Employee with ID: " + employeeId + " has been deleted successfully"));
    }




}
