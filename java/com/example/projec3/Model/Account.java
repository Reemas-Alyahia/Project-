package com.example.projec3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "VARCHAR(50) NOT NULL UNIQUE")
    @NotEmpty(message = "Account Number cannot be empty")
    @Pattern(regexp = "\\b\\d{4}-\\d{4}-\\d{4}-\\d{4}\\b", message = "Account Number must follow the format XXXX-XXXX-XXXX-XXXX")
    private String accountNumber;


    @Column(columnDefinition = "DECIMAL NOT NULL")
    @NotNull(message = "Account Balance cannot be empty")
    @Positive(message = "balance must be Positive")
    private Double balance;


    @Column(columnDefinition = "BOOLEAN NOT NULL")
    private Boolean isActive=false;


    @ManyToOne
    @JsonIgnore
    private Customer customer;
}
