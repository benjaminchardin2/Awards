package com.bencha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {
    USER("user"),
    VERIFIED_USER("verified_user");


    private final String label;
}
