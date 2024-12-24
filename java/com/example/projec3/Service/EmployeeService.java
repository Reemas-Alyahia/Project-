package com.example.projec3.Service;

import com.example.projec3.ApiResponse.ApiException;
import com.example.projec3.DTO.EmployeeDTO;
import com.example.projec3.DTO.EmployeeDTOin;
import com.example.projec3.Model.Employee;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Repository.AuthRepsitory;
import com.example.projec3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final AuthRepsitory authRepsitory;
   private final EmployeeRepository employeeRepository;


    public List<EmployeeDTO> getAllEmployees(Integer adminId) {
        MyUser admin = authRepsitory.findMyUserById(adminId);
        if (admin == null)
            throw new ApiException("Cannot Found Admin with this ID: " + adminId  );

        if (admin.getRole().equals("ADMIN")) {
            List<Employee> employees = employeeRepository.findAll();
            List<EmployeeDTO> employeeDTOS = new ArrayList<>();

            for (Employee ee : employees) {
                employeeDTOS.add(new EmployeeDTO(ee.getUser().getUsername(), ee.getUser().getName(), ee.getUser().getEmail(), ee.getPosition(), ee.getSalary()));
            }
            return employeeDTOS;
        }
        else throw new ApiException("Sorry , You do not have the authority to see this Details ");

    }


public  void registerEmployee(EmployeeDTOin employeeDTOin){
              MyUser myUser=authRepsitory.findMyUserByUsername(employeeDTOin.getUsername());
           if (myUser != null) {
                           throw new ApiException("User already exists");
                      }

                MyUser myUser1=new MyUser();

                       myUser1.setUsername(employeeDTOin.getUsername());
                       myUser1.setPassword(new BCryptPasswordEncoder().encode(employeeDTOin.getPassword()));
                      myUser1.setName(employeeDTOin.getName());
                      myUser1.setRole("EMPLOYEE");


        Employee employee=new Employee();
        employee.setId(null);
        employee.setPosition(employee.getPosition());
        employee.setSalary(employee.getSalary());

        employee.setUser(myUser1);


         authRepsitory.save(myUser1);
         employeeRepository.save(employee);
    }

    public void updateEmployee(Integer employeeId,EmployeeDTOin employeeDTOin){
        Employee employee=employeeRepository.findEmployeeById(employeeId);

        MyUser myUser=employee.getUser();
        myUser.setUsername(employeeDTOin.getUsername());
        myUser.setEmail(employeeDTOin.getEmail());
        myUser.setName(employeeDTOin.getName());
        myUser.setRole(myUser.getRole());

        /// ///
        employee.setSalary(employeeDTOin.getSalary());
        employee.setPosition(employeeDTOin.getPosition());

        authRepsitory.save(myUser);

        employeeRepository.save(employee);
    }


    public void deleteEmployee(Integer auth, Integer employeeId) {
        MyUser user = authRepsitory.findMyUserById(auth);
        if (user == null){
            throw new ApiException("Cannot found Account with this ID"+auth);}

        MyUser employee = authRepsitory.findMyUserById(employeeId);
        if (employee == null){
            throw new ApiException("Cannot found employee with this ID"+employeeId);}

        if (auth.equals(employeeId) || user.getRole().equals("ADMIN"))
            authRepsitory.delete(employee);
        else throw new ApiException("You Cannot delete this employee");
    }

}


