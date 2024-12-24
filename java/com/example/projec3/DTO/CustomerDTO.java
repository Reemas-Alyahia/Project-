package com.example.projec3.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String username;

    private String name;

    private String email;

    private String phoneNumber;

    private List<AccountDTO> accounts;
}
