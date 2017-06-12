package com.volia.eadmin.controller.user;

import com.volia.eadmin.core.controller.AbstractViewController;
import com.volia.eadmin.domain.AccessTable;
import com.volia.eadmin.repository.AccessTableRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("access")
public class AccessController extends AbstractViewController<AccessTable, AccessTableRepository> {
}
