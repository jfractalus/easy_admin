package com.volia.eadmin.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import static com.volia.eadmin.config.Constant.ROOT_PATH;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${application.security.enabled}")
    private boolean securityEnabled;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private TokenAuthenticationManager tokenAuthenticationManager;
    @Autowired
    private BaseAuthenticationManager baseAuthenticationManager;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false);
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/403");
        if (securityEnabled){
            http.formLogin().loginPage("/login").defaultSuccessUrl(ROOT_PATH).successHandler(new UrlAuthenticationSuccessHandler()).permitAll();
            addFilters(http, tokenAuthenticationFilter());
            addFilters(http, authenticationFilter());
        } else {
            addFilters(http, new DefaultAuthenticationFilter());
        }
    }

    protected void addFilters(final HttpSecurity http, GenericFilterBean filter) throws Exception {
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    private TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
        return new TokenAuthenticationFilter(tokenAuthenticationManager);
    }

    private AuthenticationFilter authenticationFilter() throws Exception {
        return new AuthenticationFilter(tokenProvider, baseAuthenticationManager);
    }
}
