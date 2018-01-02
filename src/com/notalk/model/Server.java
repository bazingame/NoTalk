/*
*
*Socket通信服务器端
*SocketDispatcher负责分发
*SocketSchedule负责检测交互时间
*
* */

package com.notalk.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {
    public static int SERVERPORT = 8888;
    public static int MAXTHREADNUM = 10;
    public static long SCHEDULEPERIOD = 5000;
    private final ServerSocket server;
    private final ExecutorService pool;

    public Server() throws IOException {
        server = new ServerSocket(SERVERPORT);
        //创建线程池(固定大小线程池)
        pool = Executors.newFixedThreadPool(MAXTHREADNUM);
    }

    public void start() throws IOException {

        //创建SocketSchedule线程池负责检测交互时间
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
        //创建并执行在给定的初始延迟之后，随后进行周期性动作
        schedule.scheduleAtFixedRate((Runnable) new SocketSchedule(),10,SCHEDULEPERIOD,TimeUnit.MILLISECONDS);

        while(true) {
            pool.execute((Runnable) new SocketDispatcher(server.accept()));
            //记录日志
//            LoggerUtil.info("ACCEPT A CLIENT AT " + new Date());
        }

    }

    public static void main(String[] args){
        try {
            new Server().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
