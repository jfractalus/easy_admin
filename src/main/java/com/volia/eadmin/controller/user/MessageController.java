package com.volia.eadmin.controller.user;

import com.volia.eadmin.core.controller.AbstractViewController;
import com.volia.eadmin.domain.Message;
import com.volia.eadmin.repository.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("message")
public class MessageController extends AbstractViewController<Message, MessageRepository> {
}
