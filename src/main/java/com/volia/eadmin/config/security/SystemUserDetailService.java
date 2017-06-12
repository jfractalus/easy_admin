package com.volia.eadmin.config.security;

import com.volia.eadmin.core.service.SecurityService;
import com.volia.eadmin.domain.SystemUser;
import com.volia.eadmin.domain.UserRole;
import com.volia.eadmin.repository.SystemUserRepository;
import org.jasypt.exceptions.EncryptionInitializationException;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SystemUserDetailService implements UserDetailsService {
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        SystemUser user = systemUserRepository.findByLogin(login);
        if(user == null){
            throw new UsernameNotFoundException("User: " + login + " not found");
        }
        try{
            return new User(user.getLogin(), securityService.decryptPassword(user.getPassword()), user.isEnabled(), true, true, true, buildGrantedAuthority(user.getUserRoles()));
        }catch (EncryptionOperationNotPossibleException | EncryptionInitializationException exc){
            throw new TokenAuthenticationException("Can't compare password: " + exc.getMessage());
        }
    }

    private Collection<GrantedAuthority> buildGrantedAuthority(Set<UserRole> adminUserRoles){
        return adminUserRoles.stream().map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());
    }
}

