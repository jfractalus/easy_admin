package com.volia.eadmin.core.service;

import org.springframework.security.access.annotation.Secured;

public interface AdminService {
    @Secured("ROLE_ADMIN")
    String decryptPassword(String hash);

    @Secured("ROLE_ADMIN")
    String encryptPassword(String password);
}
