package com.volia.eadmin.core.controller;

import com.volia.eadmin.core.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/decrypt/{hash}", method = RequestMethod.GET)
    public String decrypt(@PathVariable("hash") String hash) {
        return adminService.decryptPassword(hash);
    }

    @RequestMapping(value = "/encrypt/{password}", method = RequestMethod.GET)
    public String encrypt(@PathVariable("password") String password) {
        return adminService.encryptPassword(password);
    }
}
