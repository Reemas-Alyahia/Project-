package com.example.projec3.Repository;

import com.example.projec3.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepsitory extends JpaRepository<MyUser,Integer> {

    MyUser findMyUserByUsername(String username);

    MyUser findMyUserById(Integer id);
}
