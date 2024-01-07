package com.bencha.webservices.errors;

import com.coreoz.plume.jersey.errors.WsError;

public enum ProjectWsErrors implements WsError {
    ACCOUNT_NOT_VALIDATED,
    VERIFICATION_UNKNOWN,
    PASSWORD_MODIFICATION_UNKNOWN
}
