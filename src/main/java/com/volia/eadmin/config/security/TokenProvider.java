package com.volia.eadmin.config.security;

public interface TokenProvider {
    TokenAuthentication generateToken(String login, String password) throws Exception;
}

