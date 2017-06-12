package com.volia.eadmin.controller.user;

import com.volia.eadmin.core.controller.AbstractViewController;
import com.volia.eadmin.domain.SystemUser;
import com.volia.eadmin.repository.SystemUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController extends AbstractViewController<SystemUser, SystemUserRepository> {
}
