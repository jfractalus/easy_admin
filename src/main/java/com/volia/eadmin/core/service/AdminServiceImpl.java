package com.volia.eadmin.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private SecurityService securityService;

    @Override
    public String decryptPassword(String hash) {
        return securityService.decryptPassword(hash);
    }

    @Override
    public String encryptPassword(String password) {
        return securityService.encryptPassword(password);
    }
}
