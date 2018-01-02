/*
*
* 服务端的指挥中心
* 对不同的消息进行分发
*
* */
package com.notalk.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketDispatcher implements Runnable{
    private Socket socket;

    public SocketDispatcher(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        if(socket!=null){
            while (!socket.isClosed()){
                try {
                    //输入输出流
                    InputStream is = socket.getInputStream();
                    String line = null;
                    StringBuffer sb = null;
                    if(is.available()>0){
                        BufferedReader bufr = new BufferedReader(new InputStreamReader(is));
                        sb = new StringBuffer();
                        while (is.available()> 0 && (line = bufr.readLine()) != null ){
                            sb.append(line);
                        }

                        System.out.println(sb.toString());

                    }else {
                        Thread.sleep(Server.SCHEDULEPERIOD);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
