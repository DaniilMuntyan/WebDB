package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileDto {
    private String username;
    private String password;
    private String rpassword;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
