package com.notalk.model;

import javax.xml.crypto.Data;
import java.net.Socket;
import java.util.Date;

public class SocketWrapper {
    private Socket socket;
    private Date lastAliveTime;


    public SocketWrapper(Socket socket,Date lastAliveTime){
        this.socket = socket;
        this.lastAliveTime = lastAliveTime;
    }

    public Date getLastAliveTime() {
        return lastAliveTime;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setLastAliveTime(Date lastAliveTime) {
        this.lastAliveTime = lastAliveTime;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
