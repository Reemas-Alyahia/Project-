package com.example.projec3.Service;

import com.example.projec3.ApiResponse.ApiException;
import com.example.projec3.Model.MyUser;
import com.example.projec3.Repository.AuthRepsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService  implements UserDetailsService {

    private final AuthRepsitory authRepsitory;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser=authRepsitory.findMyUserByUsername(username);
        if(myUser==null){
            throw new ApiException("Wrong Password or Username");
        }
        return myUser;

    }
}
