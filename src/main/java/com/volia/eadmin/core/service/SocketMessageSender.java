package com.volia.eadmin.core.service;

import com.volia.eadmin.event.SocketMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SocketMessageSender implements MessageSender {
    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;
    @Value("${application.websocket.destination}")
    private String destination;

    @Override
    public void sendEvent(String path, SocketMessage socketMessage) {
        brokerMessagingTemplate.convertAndSend(destination + path, socketMessage);
    }
}
