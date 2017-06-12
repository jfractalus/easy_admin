package com.volia.eadmin.core.service;

import com.volia.eadmin.domain.UserRole;
import java.util.List;

public interface SecurityService {
    boolean comparePassword(String dbPassword, String target);
    String decryptPassword(String hash);
    String encryptPassword(String password);
    List<UserRole> getUserRoles();
}
