package com.prasanna.playload.dto;

import com.prasanna.domain.UserRole;
import lombok.Data;

@Data
public class SignupDTO {
    private String fullName;
    private String email;
    private String password;
    private String username;
    private UserRole role;
}
