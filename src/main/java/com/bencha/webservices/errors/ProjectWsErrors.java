package com.bencha.webservices.errors;

import com.coreoz.plume.jersey.errors.WsError;

public enum ProjectWsErrors implements WsError {
    ACCOUNT_NOT_VALIDATED,
    VERIFICATION_UNKNOWN,
    PASSWORD_MODIFICATION_UNKNOWN,
    USER_SUB_ALREADY_EXISTS,
    CANNOT_AUTHENTICATE_THROUGH_GOOGLE,
    RECAPTCHA_ERROR,
    INVALID_PASSWORD,
}
