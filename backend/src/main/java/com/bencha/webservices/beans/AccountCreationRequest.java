package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class AccountCreationRequest {
    private String userName;
    private String email;
    private String password;
    private Boolean rgpd;
    private String recaptcha;
}
