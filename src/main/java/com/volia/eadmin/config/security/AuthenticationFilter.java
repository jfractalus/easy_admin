package com.volia.eadmin.config.security;

import com.google.common.base.Strings;
import com.volia.eadmin.config.Constant;
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
import static com.volia.eadmin.config.Constant.PASSWORD;
import static com.volia.eadmin.config.Constant.USERNAME;

@Slf4j
public class AuthenticationFilter extends BaseTokenAuthenticationFilter {
    private TokenProvider tokenProvider;

    public AuthenticationFilter(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        String login = obtainFromRequest(request, USERNAME);
        String password = obtainFromRequest(request, PASSWORD);

        if (Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(login)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            if (authenticationIsRequired(login)) {
                TokenAuthentication tokenAuthentication = tokenProvider.generateToken(login, password);
                Authentication authentication = authenticationManager.authenticate(tokenAuthentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                response.setHeader(Constant.SECURITY_TOKEN_KEY, tokenAuthentication.getToken());
            }
        } catch (AuthenticationException failed) {
            handleAuthenticationFailure(chain, request, response, failed);
            return;
        } catch (Exception e) {
            handleAuthenticationFailure(chain, request, response, null);
            return;
        }
        chain.doFilter(request, response);
    }

}

