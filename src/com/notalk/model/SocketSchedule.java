/*
*
*检测和客户端端的最新链接时间
*若超时则关闭连接
*
* */

package com.notalk.model;

import javax.xml.crypto.Data;
import java.util.Date;

public class SocketSchedule implements Runnable {

    @Override
    public void run() {
        for (String key : SocketHolder.keySet()) {
            SocketWrapper wrapper = SocketHolder.get(key);
            if (wrapper != null && wrapper.getLastAliveTime() != null) {
                if (((new Date().getTime() - wrapper.getLastAliveTime().getTime()) / 1000) > Server.SCHEDULEPERIOD) {
                    SocketHolder.remove(key);
                }
            }
        }
    }
}
