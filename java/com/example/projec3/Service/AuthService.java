package com.example.projec3.Service;

import com.example.projec3.ApiResponse.ApiException;
import com.example.projec3.DTO.CustomerDTOin;
import com.example.projec3.Model.Account;
import com.example.projec3.Model.Customer;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Repository.AccountRepository;
import com.example.projec3.Repository.AuthRepsitory;
import com.example.projec3.Repository.CustomerRepostiory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepsitory authRepsitory;
    private final CustomerRepostiory customerRepostiory;
    private final AccountRepository accountRepository;


    public List<MyUser> getAllUsers(Integer adminId) {
        MyUser admin = authRepsitory.findMyUserById(adminId);
        if (admin == null){
            throw new ApiException("Cannot found Admin withthis  ID");
        }

        if (admin.getRole().equalsIgnoreCase("ADMIN"))
            return authRepsitory.findAll();

        else throw new ApiException("You don't have the permission to access this endpoint");
    }

    public void register(MyUser myUser){
        MyUser myUser1=authRepsitory.findMyUserByUsername(myUser.getUsername());
        if(myUser1!=null){
            throw new ApiException("User Already there");
        }
        myUser.setRole("ADMIN");
        String hashPassword=new BCryptPasswordEncoder().encode(myUser.getPassword());
        myUser.setPassword(hashPassword);

        authRepsitory.save(myUser);
    }






}
