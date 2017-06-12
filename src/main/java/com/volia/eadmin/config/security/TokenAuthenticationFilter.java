package com.volia.eadmin.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.volia.eadmin.config.Constant.SECURITY_TOKEN_KEY;
import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
public class TokenAuthenticationFilter extends BaseTokenAuthenticationFilter {

    public TokenAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = obtainFromHeader(request, SECURITY_TOKEN_KEY);
        if (isEmpty(token)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            if (authenticationIsRequired(token)) {
                TokenAuthentication tokenAuthentication = new TokenAuthentication(token);
                Authentication authentication = authenticationManager.authenticate(tokenAuthentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (AuthenticationException failed) {
            handleAuthenticationFailure(chain, request, response, failed);
            return;
        }
        chain.doFilter(request, response);
    }
}
