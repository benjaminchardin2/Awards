package com.bencha.api.google.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor()
public class CaptchaVerifyResponse {
    private String hostname;
    private Boolean success;
}
