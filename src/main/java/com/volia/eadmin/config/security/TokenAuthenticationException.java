package com.volia.eadmin.config.security;

import org.springframework.security.core.AuthenticationException;

public class TokenAuthenticationException extends AuthenticationException {
    public TokenAuthenticationException(String description) {
        super(description);
    }
}