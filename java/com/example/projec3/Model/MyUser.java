package com.example.projec3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(columnDefinition = "VARCHAR(10) NOT NULL UNIQUE")
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    private String username;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    @NotEmpty(message = "User Password cannot be empty")
    @Size(min = 6, max = 255, message = "User Password must be at least 6 characters")
    private String password;

    @Column(columnDefinition = "VARCHAR(20) NOT NULL")
    @NotEmpty(message = "User Name cannot be empty")
    @Size(min = 2, max = 20, message = "User Name must be between 2 and 20 characters")
    private String name;


    @Column(columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    @NotEmpty(message = "User Email cannot be empty")
    @Email(message = "User Email must be a valid email format")
    private String email;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$", message = "User Role must be either 'CUSTOMER', 'EMPLOYEE', or 'ADMIN' only")
    private String role;




    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Customer customer;

@OneToOne(cascade = CascadeType.ALL)
@PrimaryKeyJoinColumn
private Employee employee;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
