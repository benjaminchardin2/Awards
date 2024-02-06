package com.bencha.webservices.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginCredentials {
    private String email;
    private String password;
}
