package com.volia.eadmin.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public abstract class BaseAuthenticationFilter extends GenericFilterBean {
    protected AuthenticationManager authenticationManager;

    public BaseAuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    protected boolean authenticationIsRequired(final String agreement) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }
        if (existingAuth instanceof UsernamePasswordAuthenticationToken && !existingAuth.getName().equals(agreement)) {
            return true;
        }
        return existingAuth instanceof AnonymousAuthenticationToken;
    }

    protected String obtainFromHeader(final HttpServletRequest request, final String headerKey) {
        return request.getHeader(headerKey);
    }

    protected String obtainFromRequest(HttpServletRequest request, String param){
        return request.getParameter(param);
    }

    protected void handleAuthenticationFailure(FilterChain chain, HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        if (failed instanceof TokenAuthenticationException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("ERROR", failed.getMessage());
        }
        chain.doFilter(request, response);
    }
}

