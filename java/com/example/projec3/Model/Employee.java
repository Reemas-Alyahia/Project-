package com.example.projec3.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    private Integer id;

    @Column(columnDefinition = "VARCHAR(50) NOT NULL")
    @NotEmpty(message = "Employee Position cannot be empty")
    private String position;

    @Column(columnDefinition = "DECIMAL NOT NULL")
    @Positive(message = "Employee Salary must be a Positive number")
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser user;


}
