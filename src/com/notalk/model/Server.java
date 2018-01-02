package com.notalk.model;

/*
*
*Socket通信服务器端
*SocketDispatcher负责分发
*SocketSchedule负责检测交互时间
*
* */


import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private final ServerSocket Server;


    public Server() throws IOException {
        Server = new ServerSocket();
        
    }



}
