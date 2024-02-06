package com.bencha.webservices.beans;

import com.bencha.db.generated.User;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class AuthenticatedSimpleUser {

    private final User user;
    private final Set<String> permissions;

}
