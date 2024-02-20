package com.bencha.webservices.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class FrontendConfiguration {
    private Boolean isAccountEnabled;
    private LocalDateTime localStorageResetDate;
}
