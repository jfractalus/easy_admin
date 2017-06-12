package com.volia.eadmin.core.service;

import com.volia.eadmin.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private PBEStringCleanablePasswordEncryptor passwordEncryptor;

    @Override
    public String decryptPassword(String hash) {
        return passwordEncryptor.decrypt(hash);
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncryptor.encrypt(password);
    }

    @Override
    public boolean comparePassword(String dbPassword, String password) {
        return password.equals(dbPassword);
    }

    @Override
    public List<UserRole> getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || isEmpty(authentication.getAuthorities())){
            throw new RuntimeException("Access denied");
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream().map(auth -> UserRole.valueOf(auth.getAuthority())).collect(Collectors.toList());
    }
}
