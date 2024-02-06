package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@NoArgsConstructor
public class Profile {
    private String userName;
    private String email;
    private String userHashtag;
    private Boolean isGoogle;
}
