package com.volia.eadmin.config.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import java.util.HashMap;
import java.util.Map;

public final class TokenBuilder {
    private String securityKey;
    private DateTime expirationDate;
    private Map<String, Object> tokenAttributes = new HashMap<>();

    public TokenBuilder(DateTime expirationDate, String securityKey) {
        this.expirationDate = expirationDate;
        this.securityKey = securityKey;
    }

    public TokenBuilder addAttribute(String key, Object value){
        tokenAttributes.put(key, value);
        return this;
    }

    public String buildToken(){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setExpiration(expirationDate.toDate());
        jwtBuilder.setClaims(tokenAttributes);
        return jwtBuilder.signWith(SignatureAlgorithm.HS256, securityKey).compact();
    }
}
