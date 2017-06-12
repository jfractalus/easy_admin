package com.volia.eadmin.core.service;

import com.volia.eadmin.event.SocketMessage;

public interface MessageSender {
    void sendEvent(String path, SocketMessage socketMessage);
}
