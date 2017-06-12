package com.volia.eadmin.event;

import lombok.Getter;

@Getter
public class SocketMessage {
    private String fromUser;
    private String body;
    private boolean delete;

    public SocketMessage(String fromUser, String body) {
        this.fromUser = fromUser;
        this.body = body;
    }

    public SocketMessage(String fromUser, boolean delete) {
        this.fromUser = fromUser;
        this.delete = delete;
    }
}
