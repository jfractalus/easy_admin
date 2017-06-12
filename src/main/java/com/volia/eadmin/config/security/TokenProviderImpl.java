package com.volia.eadmin.config.security;

import com.volia.eadmin.core.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.volia.eadmin.util.DateTimeUtil.dateTimeNow;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class TokenProviderImpl implements TokenProvider {
    @Autowired
    private SystemUserDetailService systemUserDetailService;
    @Value("${application.security.tokenExpireAfterMinutes:30}")
    private int tokenExpireAfterMinutes;
    @Value("${application.security.key}")
    private String securityKey;
    @Autowired
    private SecurityService securityService;

    @Override
    public TokenAuthentication generateToken(String login, String password) throws Exception {
        if (isEmpty(login) || isEmpty(password)){
            throw new TokenAuthenticationException("Can't generate token");
        }
        User user = (User) systemUserDetailService.loadUserByUsername(login);
        if(!securityService.comparePassword(user.getPassword(), password)){
            throw new TokenAuthenticationException("Authentication error");
        }
        return buildFullTokenAuthentication(user, buildToken(user));
    }

    private String buildToken(User user){
        return new TokenBuilder(dateTimeNow().plusMinutes(tokenExpireAfterMinutes), securityKey)
                .addAttribute("login", user.getUsername())
                .addAttribute("token_create_date", dateTimeNow().getMillis())
                .addAttribute("token_expiration_date", dateTimeNow().plusMinutes(tokenExpireAfterMinutes).getMillis())
                .buildToken();
    }

    private TokenAuthentication buildFullTokenAuthentication(User user, String token) {
        if (!user.isEnabled()) {
            throw new TokenAuthenticationException("User disabled");
        }
        if(isEmpty(token)){
            throw new TokenAuthenticationException("Can't create token");
        }
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        return new TokenAuthentication(token, authorities, true, user);
    }
}
