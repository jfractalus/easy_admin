package com.volia.eadmin.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.volia.eadmin.util.DateTimeUtil.dateTimeNow;

@Slf4j
@Component
public class TokenAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${application.security.key}")
    private String securityKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof TokenAuthentication) {
            return processAuthentication((TokenAuthentication) authentication);
        } else {
            authentication.setAuthenticated(false);
            return authentication;
        }
    }

    private TokenAuthentication processAuthentication(TokenAuthentication authentication) throws AuthenticationException {
        String token = authentication.getToken();
        DefaultClaims claims;
        try {
            claims = (DefaultClaims) Jwts.parser().setSigningKey(securityKey).parse(token).getBody();
        }catch(Exception exc){
            throw new TokenAuthenticationException("Token corrupted");
        }
        if (checkExpirationDate(claims)){
            throw new TokenAuthenticationException("Token expired");
        }
        return buildFullTokenAuthentication(authentication, claims);
    }

    private boolean checkExpirationDate(DefaultClaims claims){
        Long expirationMilliseconds = claims != null ? (Long)claims.get("token_expiration_date") : null;
        return expirationMilliseconds == null || new DateTime(Long.valueOf(expirationMilliseconds)).isBefore(dateTimeNow());
    }

    private TokenAuthentication buildFullTokenAuthentication(TokenAuthentication authentication, DefaultClaims claims) {
        String login = claims.get("login", String.class);
        UserDetails user = userDetailsService.loadUserByUsername(login);
        if (user != null && user.isEnabled()) {
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) user.getAuthorities();
            return new TokenAuthentication(authentication.getToken(), authorities, true, user);
        } else {
            throw new TokenAuthenticationException("User disabled");
        }
    }
}
