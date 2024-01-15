package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class NewPassword {
    private String oldPassword;
    private String newPassword;
}
